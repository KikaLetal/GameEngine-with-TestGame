    package Engine.Components;
    
    import Engine.Math.Vector2f;
    import Engine.esc.Component;
    
    public class BoxCollider extends Component {
        Vector2f size;
        Vector2f position;
        boolean isTrigger;
    
        public BoxCollider(Vector2f position, Vector2f size){
            this.size = size;
            this.position = position;
            isTrigger = false;
        }
    
        public BoxCollider(Vector2f position, Vector2f size, boolean isTrigger){
            this.size = size;
            this.position = position;
            this.isTrigger = isTrigger;
        }
    
        public void setPosition(Vector2f position) {
            this.position = position;
        }
    
        public void setSize(Vector2f size) {
            this.size = size;
        }
    
        public void setTrigger(boolean trigger) {
            isTrigger = trigger;
        }

        public Vector2f getPosition() {
            return position;
        }
    
        public Vector2f getSize() {
            return size;
        }

        public boolean isTrigger() {
            return isTrigger;
        }
    }
