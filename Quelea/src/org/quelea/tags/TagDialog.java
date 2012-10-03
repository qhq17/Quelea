/* 
 * This file is part of Quelea, free projection software for churches.
 * Copyright (C) 2011 Michael Berry
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
package org.quelea.tags;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import org.quelea.Application;
import org.quelea.languages.LabelGrabber;
import org.quelea.utils.Utils;
import org.quelea.windows.library.LibrarySongList;

/**
 * A dialog used for finding songs with certain tags.
 * @author Michael
 */
public class TagDialog extends JDialog {

    private TagEntryPanel tagEntryPanel;
    private LibrarySongList list;

    /**
     * Create a new tag dialog.
     */
    public TagDialog() {
//        super(Application.get().getMainWindow(), LabelGrabber.INSTANCE.getLabel("filter.tag"), ModalityType.MODELESS);
        setIconImage(Utils.getImage("icons/tag.png", 16, 16));
        list = new LibrarySongList(false);
        
        setLayout(new BorderLayout());
        tagEntryPanel = new TagEntryPanel(list, false, true);
        add(tagEntryPanel, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(null);
        scroll.setPreferredSize(new Dimension(500, 500));
        add(scroll, BorderLayout.CENTER);
        pack();
    }
    
    /**
     * Force a reload of all the tags in the panel.
     */
    public void reloadTags() {
        tagEntryPanel.reloadTags();
    }

    /**
     * Overrides the default behaviour so that when set to visible the tags
     * are reloaded and centred on parent.
     * @param visible true if visible, false otherwise.
     */
    @Override
    public void setVisible(boolean visible) {
        reloadTags();
        setLocationRelativeTo(getParent());
        super.setVisible(visible);
    }

    /**
     * Testing.
     * @param args 
     */
    public static void main(String[] args) {
        TagDialog tagDialog = new TagDialog();
        tagDialog.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        tagDialog.setVisible(true);
    }
}
