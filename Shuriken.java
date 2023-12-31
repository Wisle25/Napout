public class Shuriken extends Obstacle
{
    // ----- Lifecycle ---------- //

    public Shuriken(int Rotation, int Speed)
    {
        Anim        = new AnimationComponent(this, "images/Traps/Shuriken/Shuriken", 2);
        Anim.SetScale(80);
        
        this.Speed  = Speed;
        this.Damage = 15;

        setRotation(Rotation);
    }

    public void act()
    {
        super.act();

        Shoot();
    }

    // ----- Combat ---------- //

    private int Speed;
    private int Distance    = 0;
    private int MaxDistance = 300;

    private void Shoot()
    {
        boolean Player = getOneIntersectingObject(Player.class) != null;
        boolean Ground = getOneIntersectingObject(Ground.class) != null;

        if (Player || Ground || Distance > MaxDistance)
        {
            Distance = 0;
            getWorldOfType(UWorld.class).RemoveObject(this);
        }
        else
        {
            Distance += Speed;
            move(Speed);
        }
    }
}
