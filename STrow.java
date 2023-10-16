public class STrow extends Environment
{
    // ----- Lifecycle ---------- //

    public STrow(int Rotation, int Speed)
    {
        setImage("images/Traps/Shuriken/STrow.png");
        getImage().scale(20, 20);
        setRotation(Rotation);
        CreateShuriken(Rotation, Speed);
    }

    public void act()
    {
        super.act();

        Shoot();
    }

    // ----- Combat ---------- //

    private Shuriken[] Shurikens = new Shuriken[3];

    private int ShootTimer = 150;
    private int EachShootTimer = 10;
    private int Count = 0;

    private boolean Shooting = false;

    private void CreateShuriken(int Rotation, int Speed)
    {
        for (int I = 0; I < 3; ++I)
            Shurikens[I] = new Shuriken(getRotation(), Speed);
    }

    private void Shoot()
    {
        UWorld World = getWorldOfType(UWorld.class);

        boolean bCanShoot  = World.GetTimerManager().IsTimerFinished("ShootTimer");
        boolean bShootEach = World.GetTimerManager().IsTimerFinished("EachShootTimer");
        
        if (bCanShoot)
        {
            Shooting = true;
        }

        if (Shooting && bShootEach && Count < 3)
        {
            World.AddObject(Shurikens[Count++], getX(), getY());

            World.GetTimerManager().StartTimer("EachShootTimer", EachShootTimer);
        }
        else if (Shooting && bShootEach && Count == 3)
        {
            Count = 0;
            Shooting = false;

            // Create new timer to shoot
            World.GetTimerManager().StartTimer("ShootTimer", ShootTimer);
        }
    } 
}