import java.util.Map;

import greenfoot.*;

public class Entity extends Actor
{
    // ----- Lifecycle ---------- //
    
    public Entity()
    {
        SetupAnimation();
        SetupMovement();
    }
    
    public void act()
    {
        HandleState();
        if (Animations != null && !Animations.isEmpty())
        Animations.get(EState).TickComponent();
        Movement              .TickComponent();
    }

    // ----- Properties ---------- //

    protected int Direction = 1;
    protected EntityState EState = EntityState.IDLE;

    public void SetState(EntityState Value) 
    { 
        if (EState == Value) return;

        EntityState LastState = EState;
        EState                = Value; 

        // Reset last animation state
        if (Animations.containsKey(LastState))
            Animations.get(LastState).Reset();

        // Setup new animation
        if (Animations.containsKey(EState))
            Animations.get(EState).Setup();
    }

    protected void HandleState()
    {
    }

    // ----- Attributes ---------- //

    private int MaxHealth = 100;
    private int Health    = MaxHealth;

    public void ReceiveDamage(int Damage)
    {
        Health = Health - Damage <= 0 ? 0 : Health - Damage;
        System.out.println(this.toString() + " health is now " + Health);

        if (Health == 0)
            Die();
    }

    private void Die()
    {
        // Play die animation
        SetState(EntityState.DIE);
    }

    // ----- Components ---------- //

    protected Map<EntityState, AnimationComponent> Animations;
    protected MovementComponent Movement;

    /* Should setup animation here */
    protected void SetupAnimation()
    {
        // Do nothing in here
    }

    protected void SetupMovement()
    {
        Movement = new MovementComponent(this);
    }

    // ----- Physics ---------- //

    public boolean IsOnGround()
    {
        Actor Ground = getOneObjectAtOffset(0, getImage().getHeight() / 2, Ground.class);

        return Ground != null;
    }

    public Actor GetGround()
    {
        return getOneObjectAtOffset(0, getImage().getHeight() / 2, Ground.class);
    }
}
