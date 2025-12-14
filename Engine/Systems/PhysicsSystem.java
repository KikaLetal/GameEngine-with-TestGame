package Engine.Systems;

import Engine.Math.Vector2f;
import Engine.Systems.Events.CollisionEnterEvent;
import Engine.Systems.Events.CollisionExitEvent;
import Engine.Systems.Events.CollisionStayEvent;
import Engine.esc.*;
import Engine.esc.System;

import Engine.Components.*;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class PhysicsSystem extends System {
    private Map<Entity, Set<Entity>> previousCollisions = new HashMap<>();
    private final Vector2f GRAVITY = new Vector2f(0, 9.8f * 10);
    float EPS = 1e-6f;

    public PhysicsSystem(){
        setPriority(0);
    }

    @Override
    public void update(EntityManager em, float deltaTime){
        syncCollidersWithTransforms(em);

        updateRigidBodies(em, deltaTime);

        for(int i = 0; i < 2; i++) {
            resolveCollisions(em);
        }

        Map<Entity, Set<Entity>> currentCollisions = collectCollisions(em);
        emitCollisionEvents(previousCollisions, currentCollisions);
        previousCollisions = currentCollisions;


        syncTransformsWithColliders(em);
    }

    @Override
    public void debugUpdate(EntityManager em, float deltaTime){
        syncCollidersWithTransforms(em);

        updateRigidBodies(em, deltaTime);

        for(int i = 0; i < 2; i++) {
            resolveCollisions(em);
        }

        Map<Entity, Set<Entity>> currentCollisions = collectCollisions(em);
        emitCollisionEvents(previousCollisions, currentCollisions);
        previousCollisions = currentCollisions;


        syncTransformsWithColliders(em);
    }

    private void updateRigidBodies(EntityManager em, float deltaTime) {
        List<Entity> entities = em.getEntitiesWithComponents(RigidBody.class);

        for(Entity ent: entities){
            RigidBody rb = ent.getComponent(RigidBody.class);
            Transform tr = ent.getComponent(Transform.class);

            if(tr == null || rb.getIskinematic()){
                continue;
            }

            if(rb != null) rb.setOnGround(false);

            if(!rb.isOnGround() && rb.getGravity() != 0 && !rb.getIskinematic()){
                Vector2f gravityForce = Vector2f.multiply(GRAVITY.multiply(rb.getGravity()/2), deltaTime);
                rb.setVelocity(rb.getVelocity().add(gravityForce));
            }

            Vector2f displacement = Vector2f.multiply(rb.getVelocity(), deltaTime);

            BoxCollider collider = ent.getComponent(BoxCollider.class);

            if (collider != null) {
                collider.setPosition(Vector2f.add(collider.getPosition(), displacement));
            }
        }
    }

    private void resolveCollisions(EntityManager em){
        List<Entity> Collaidables = em.getEntitiesWithComponents(BoxCollider.class);

        for(int i =0; i < Collaidables.size(); i++){
            Entity A = Collaidables.get(i);
            RigidBody rbA = A.getComponent(RigidBody.class);
            BoxCollider bcA = A.getComponent(BoxCollider.class);
            if(bcA == null) continue;

            for(int j = i + 1; j < Collaidables.size(); j++){
                Entity B = Collaidables.get(j);
                RigidBody rbB = B.getComponent(RigidBody.class);
                BoxCollider bcB = B.getComponent(BoxCollider.class);
                if(bcB == null) continue;

                if(IsCollision(bcA, bcB)){
                    if(!bcA.isTrigger() && !bcB.isTrigger())
                        resolveCollision(bcA, bcB, rbA, rbB);
                }
            }

        }
    }

    private Map<Entity, Set<Entity>> collectCollisions(EntityManager em){
        Map<Entity, Set<Entity>> Collisions = new HashMap<>();

        List<Entity> Collaidables = em.getEntitiesWithComponents(BoxCollider.class);

        for(int i =0; i < Collaidables.size(); i++){
            Entity A = Collaidables.get(i);
            BoxCollider bcA = A.getComponent(BoxCollider.class);
            if(bcA == null) continue;

            for(int j = i + 1; j < Collaidables.size(); j++){
                Entity B = Collaidables.get(j);
                BoxCollider bcB = B.getComponent(BoxCollider.class);
                if(bcB == null) continue;

                if(IsCollision(bcA, bcB)){
                    Collisions.computeIfAbsent(A, k -> new HashSet<>()).add(B);
                    Collisions.computeIfAbsent(B, k -> new HashSet<>()).add(A);
                }
            }

        }

        return Collisions;
    }

    private void emitCollisionEvents(Map<Entity, Set<Entity>> prev, Map<Entity, Set<Entity>> cur){
        for(Entity A : cur.keySet()){
            for(Entity B : cur.get(A)){
                if(prev.getOrDefault(A, Set.of()).contains(B)){
                    EventSystem.get().emit(new CollisionStayEvent(A, B));
                }
                else EventSystem.get().emit(new CollisionEnterEvent(A, B));
            }
        }

        for(Entity A : prev.keySet()){
            for(Entity B : prev.get(A)){
                if(!cur.getOrDefault(A, Set.of()).contains(B)){
                    EventSystem.get().emit(new CollisionExitEvent(A, B));
                }
            }
        }
    }

    private void syncCollidersWithTransforms(EntityManager em) {
        List<Entity> entities = em.getEntitiesWithComponents(BoxCollider.class, Transform.class);

        for (Entity ent : entities) {
            Transform tr = ent.getComponent(Transform.class);
            BoxCollider col = ent.getComponent(BoxCollider.class);

            if (tr != null && col != null) {
                col.setPosition(tr.getPosition());
            }
        }
    }

    private void syncTransformsWithColliders(EntityManager em) {
        List<Entity> entities = em.getEntitiesWithComponents(BoxCollider.class, Transform.class);

        for (Entity ent : entities) {
            Transform tr = ent.getComponent(Transform.class);
            BoxCollider col = ent.getComponent(BoxCollider.class);

            if (tr != null && col != null) {
                tr.setPosition(col.getPosition());
            }
        }
    }

    private boolean IsCollision(BoxCollider A, BoxCollider B){
        if (A.getPosition().x < B.getPosition().x + B.getSize().x
                && A.getPosition().y < B.getPosition().y + B.getSize().y
                && A.getPosition().x + A.getSize().x > B.getPosition().x
                && A.getPosition().y + A.getSize().y > B.getPosition().y) {
            return true;
        }
        return false;
    }

    private void resolveCollision(BoxCollider bcA, BoxCollider bcB, RigidBody rbA, RigidBody rbB){
        boolean kinematicA = (rbA == null) || rbA.getIskinematic();
        boolean kinematicB = (rbB == null) || rbB.getIskinematic();

        if(kinematicA && kinematicB) return;

        float overlapX = min(bcA.getPosition().x + bcA.getSize().x, bcB.getPosition().x + bcB.getSize().x) - max(bcA.getPosition().x, bcB.getPosition().x);
        float overlapY = min(bcA.getPosition().y + bcA.getSize().y, bcB.getPosition().y + bcB.getSize().y) - max(bcA.getPosition().y, bcB.getPosition().y);

        if(overlapX < EPS && overlapY < EPS) return;

        Vector2f centerA = new Vector2f(bcA.getPosition().x + bcA.getSize().x /2, bcA.getPosition().y + bcA.getSize().y /2);
        Vector2f centerB = new Vector2f(bcB.getPosition().x + bcB.getSize().x /2, bcB.getPosition().y + bcB.getSize().y /2);

        float invWeightA = kinematicA ? 0f : 1f / max(rbA.getMass(), EPS);
        float invWeightB = kinematicB ? 0f : 1f / max(rbB.getMass(), EPS);
        float totalInvWeight = invWeightA + invWeightB;

        if(totalInvWeight == 0) return;

        Vector2f velA = rbA != null ? rbA.getVelocity() : Vector2f.ZERO();
        Vector2f velB = rbB != null ? rbB.getVelocity() : Vector2f.ZERO();
        Vector2f relVel = velA.subtract(velB);

        boolean resolveY;

        if(Math.abs(relVel.y) > Math.abs(relVel.x)) resolveY = true;
        else resolveY = overlapY < overlapX;

        if(!resolveY){
            float direction = centerA.x < centerB.x ? -1.f : 1f;

            float moveA =  direction * overlapX * (invWeightA / totalInvWeight);
            float moveB = -direction * overlapX * (invWeightB / totalInvWeight);

            bcA.setPosition(new Vector2f(bcA.getPosition().x + moveA, bcA.getPosition().y));
            bcB.setPosition(new Vector2f(bcB.getPosition().x + moveB, bcB.getPosition().y));

            if(rbA != null && !kinematicA && rbA.getVelocity().x * direction > 0) rbA.setVelocity(new Vector2f(0, rbA.getVelocity().y));
            if(rbB != null && !kinematicB && rbB.getVelocity().x * (-direction) > 0) rbB.setVelocity(new Vector2f(0, rbB.getVelocity().y));
        }
        else{
            float direction = centerA.y < centerB.y ? -1.f : 1f;

            float moveA =  direction * overlapY * (invWeightA / totalInvWeight);
            float moveB = -direction * overlapY * (invWeightB / totalInvWeight);

            bcA.setPosition(new Vector2f(bcA.getPosition().x, bcA.getPosition().y + moveA));
            bcB.setPosition(new Vector2f(bcB.getPosition().x, bcB.getPosition().y + moveB));

            if(rbA != null && !kinematicA && rbA.getVelocity().y > 0 && direction == -1) {
                rbA.setVelocity(new Vector2f(rbA.getVelocity().x, 0));
                if (bcA.getPosition().y + bcA.getSize().y /2 < bcB.getPosition().y + bcB.getSize().y /2) rbA.setOnGround(true);
            }
            if(rbB != null && !kinematicB && rbB.getVelocity().y < 0 && direction == 1){
                rbB.setVelocity(new Vector2f(rbB.getVelocity().x, 0));
                if (bcB.getPosition().y + bcB.getSize().y /2 < bcA.getPosition().y + bcA.getSize().y /2) rbB.setOnGround(true);
            }
        }
    }
}

