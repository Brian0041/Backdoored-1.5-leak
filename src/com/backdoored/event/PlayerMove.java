package com.backdoored.event;

public abstract class PlayerMove extends BackdooredEvent
{
    public float yaw;
    public float pitch;
    public boolean onGround;
    
    public PlayerMove(final float yaw, final float pitch, final boolean onGround) {
        super();
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }
    
    public static class Pre extends PlayerMove
    {
        public Pre(final float yaw, final float pitch, final boolean onGround) {
            super(yaw, pitch, onGround);
        }
    }
    
    public static class Post extends PlayerMove
    {
        public Post(final float yaw, final float pitch, final boolean onGround) {
            super(yaw, pitch, onGround);
        }
    }
}
