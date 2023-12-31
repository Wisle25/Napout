import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Map;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

/**
 * Write a description of class MainMenu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MainMenu extends World
{
    // ===== Lifecycle ========== //

    String UserName;

    public MainMenu()
    {    
        super(800, 600, 1);

        setBackground("images/Bg/menufinal.png");

        // Adding buttons
        String Path = "images/Buttons/";

        /* Play Button */
        Buttons.put("PlayBtn", new Button(Path + "PlayBtn.png", Path + "HvrPlayBtn.png"));
        Buttons.get("PlayBtn").OnPressed(this::PlayBtnPressed);
        addObject(Buttons.get("PlayBtn"), 400, 250);

        /* How to play Button */
        Buttons.put("HTPlayBtn", new Button(Path + "HowToPlayBtn.png", Path + "HvrHowToPlayBtn.png"));
        Buttons.get("HTPlayBtn").SetScale(.6, .6);
        Buttons.get("HTPlayBtn").OnPressed(this::HTPlayBtnPressed);
        addObject(Buttons.get("HTPlayBtn"), 400, 350);

        /* Credit Button */
        Buttons.put("CreditBtn", new Button(Path + "CreditBtn.png", Path + "HvrCreditBtn.png"));
        Buttons.get("CreditBtn").SetScale(0.5, 0.5);
        Buttons.get("CreditBtn").OnPressed(this::CreditBtnPressed);
        addObject(Buttons.get("CreditBtn"), 400, 450);

        Greenfoot.start();
        prepare();
    }

    public void act()
    {
        AskUsername();

        if (Greenfoot.isKeyDown("backspace") && bDisplaying)
        {
            bDisplaying = false;
            setBackground(MainBg);

            Buttons.forEach((Key, Value) -> {
                        Value.Hide(false);
                });
        }
    }

    // ===== Properties ========== //

    String MainBg = "images/Bg/menufinal.png";
    String HTPlay = "images/Contents/Howtoplay.png";
    String Credit = "images/Contents/Credit.png";

    boolean Asked = false;

    boolean bDisplaying = false;

    Map<String, Button> Buttons = new HashMap<>();

    // ===== Button Handler ========== //

    private void PlayBtnPressed()
    {
        if (!Check()) return;

        Greenfoot.playSound("sfx_scene.wav");
        Greenfoot.setWorld(new TransitionWorld(new Level1(), "Op", 350));
    }

    private void HTPlayBtnPressed()
    {
        if (!Check()) return;

        setBackground(HTPlay);
    }

    private void CreditBtnPressed()
    {
        if (!Check()) return;

        setBackground(Credit);
    }

    private boolean Check()
    {
        if (bDisplaying) return false;

        bDisplaying = true;
        Buttons.forEach((Key, Value) -> {
                    Value.Hide(true);
            });

        return true;
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
    }

    private void AskUsername()
    {
        if (Asked) return;

        while (!Asked)
        {
            UserName = Greenfoot.ask("Insert your username: (Only lowercase allowed, max 10 characters, minimum 1 digit)");

            if (UserName.matches("(?=.*\\d)[a-z\\d]{1,10}"))
            {
                Asked = true;
            }
        }

        getBackground().drawImage(new GreenfootImage("Welcome ".concat(UserName), 20, Color.RED, null), 20, 20);

        // Save to file
        String filePath = "Score.txt";
        Path Score = Path.of(filePath);

        try {
            BufferedWriter writer = Files.newBufferedWriter(Score, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            writer.write(UserName);
            writer.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
