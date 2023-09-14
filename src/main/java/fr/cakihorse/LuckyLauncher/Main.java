package fr.cakihorse.LuckyLauncher.app;

import fr.cakihorse.Images;
import fr.cakihorse.LuckyLauncher.Launcher;
import fr.cakihorse.LuckyLauncher.utils.Resources;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.util.Saver;

import javax.swing.*;
import java.io.File;


public class Frame extends JFrame {

    private static File saverFile = new File(String.valueOf(Launcher.getPath()), "user.infos");
    private static Saver saver = new Saver(saverFile);

    private static Frame instance;
    private Panel panel;


    public static void main(String[] args) {
        MicrosoftAuthenticator microsoftAuthenticator = new MicrosoftAuthenticator();
        final String refresh_token = getSaver().get("refresh_token");
        MicrosoftAuthResult result = null;

        if (refresh_token != null && !refresh_token.isEmpty()) {
            try {
                result = microsoftAuthenticator.loginWithRefreshToken(refresh_token);
            } catch (MicrosoftAuthenticationException ex) {

                throw new RuntimeException(ex);
            }
            Launcher.authInfos = new AuthInfos(result.getProfile().getName(), result.getAccessToken(), result.getProfile().getId());
            System.out.printf("Logged in with '%s'%n", result.getProfile().getName());

        }


        JFrame f = new JFrame();

        f.setTitle("LuckyLaunch");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setSize(1280, 720);
        f.setResizable(false);
        f.setUndecorated(false);
       f.setLocationRelativeTo(null);
       f.setIconImage(Resources.getResource("luckyUranium.png"));


        f.setContentPane(new Panel());

        f.setVisible(true);
    }
    public static Saver getSaver() {
        return saver;
    }
}
