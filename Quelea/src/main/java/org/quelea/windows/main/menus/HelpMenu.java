/*
 * This file is part of Quelea, free projection software for churches.
 * 
 * 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.quelea.windows.main.menus;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.javafx.dialog.Dialog;
import org.quelea.services.languages.LabelGrabber;
import org.quelea.services.utils.LoggerUtils;
import org.quelea.services.utils.QueleaProperties;
import org.quelea.services.utils.UpdateChecker;
import org.quelea.windows.help.AboutDialog;
import org.quelea.utils.ThreadedDesktop;

/**
 * Quelea's help menu.
 * <p/>
 * @author Michael
 */
public class HelpMenu extends Menu {

    private static final Logger LOGGER = LoggerUtils.getLogger();
    private final MenuItem queleaManual;
    private final MenuItem queleaFacebook;
    private final MenuItem queleaDiscuss;
    private final MenuItem queleaWiki;
    private final MenuItem updateCheck;
    private final MenuItem about;
    private AboutDialog aboutDialog;

    /**
     * Create a new help menu
     */
    public HelpMenu() {
        super(LabelGrabber.INSTANCE.getLabel("help.menu"));

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                aboutDialog = new AboutDialog();
            }
        });

        if (Desktop.isDesktopSupported()) {
            queleaManual = new MenuItem(LabelGrabber.INSTANCE.getLabel("help.menu.manual"), new ImageView(new Image("file:icons/manual.png", 16, 16, false, true)));
            queleaManual.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent t) {
                    launchPage("http://quelea.org/manuals/get.php?lang=" + QueleaProperties.get().getLanguageFile().getName());
                }
            });
            getItems().add(queleaManual);
            queleaFacebook = new MenuItem(LabelGrabber.INSTANCE.getLabel("help.menu.facebook"), new ImageView(new Image("file:icons/facebook.png", 16, 16, false, true)));
            queleaFacebook.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent t) {
                    launchPage(QueleaProperties.get().getFacebookPageLocation());
                }
            });
            getItems().add(queleaFacebook);
            queleaDiscuss = new MenuItem(LabelGrabber.INSTANCE.getLabel("help.menu.discussion"), new ImageView(new Image("file:icons/discuss.png", 16, 16, false, true)));
            queleaDiscuss.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent t) {
                    launchPage(QueleaProperties.get().getDiscussLocation());
                }
            });
            getItems().add(queleaDiscuss);
            queleaWiki = new MenuItem(LabelGrabber.INSTANCE.getLabel("help.menu.wiki"), new ImageView(new Image("file:icons/wiki.png", 16, 16, false, true)));
            queleaWiki.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent t) {
                    launchPage(QueleaProperties.get().getWikiPageLocation());
                }
            });
            getItems().add(queleaWiki);
        } else {
            queleaDiscuss = null;
            queleaFacebook = null;
            queleaWiki = null;
            queleaManual = null;
        }
        updateCheck = new MenuItem(LabelGrabber.INSTANCE.getLabel("help.menu.update"), new ImageView(new Image("file:icons/update.png", 16, 16, false, true)));
        updateCheck.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent t) {
                new UpdateChecker().checkUpdate(true, true, true);
            }
        });
        getItems().add(updateCheck);
        about = new MenuItem(LabelGrabber.INSTANCE.getLabel("help.menu.about"), new ImageView(new Image("file:icons/about.png", 16, 16, false, true)));
        about.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent t) {
                aboutDialog.show();
            }
        });
        getItems().add(about);
    }

    private void launchPage(String page) {
        ThreadedDesktop.browse(page, (ex) -> {
            LOGGER.log(Level.WARNING, "Couldn't launch Quelea Facebook page", ex);
            showError(page);
        });
    }

    /**
     * Show a dialog saying we couldn't open the given location.
     * <p/>
     * @param location the location that failed to open.
     */
    private void showError(String page) {
        Platform.runLater(() -> {
            Dialog.showError(LabelGrabber.INSTANCE.getLabel("help.menu.error.title"), LabelGrabber.INSTANCE.getLabel("help.menu.error.text").replace("$1", page));
        });
    }
}
