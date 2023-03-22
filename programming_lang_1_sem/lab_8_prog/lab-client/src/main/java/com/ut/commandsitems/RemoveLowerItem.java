package com.ut.commandsitems;

import javax.swing.JPanel;

import com.ut.client.ConnectionAndExecutorManager;
import com.ut.common.commands.CommandResult;
import com.ut.common.data.SpaceMarine;
import com.ut.gui.AddJPanel;
import com.ut.gui.CommandModeJPanel;
import com.ut.util.ConstantsLanguage;

public class RemoveLowerItem implements ActionWhenSelected {

    @Override
    public void showNeededPanels(JPanel jPanel) {
        CommandModeJPanel commandJPanel = (CommandModeJPanel) jPanel;
        commandJPanel.setAargumentTextFieldEditable(false);
        commandJPanel.addAddJPanel();
        commandJPanel.updateNorthPanel();
    }

    @Override
    public void executeCommand(JPanel jPanel, ConnectionAndExecutorManager caeManager) {
        CommandModeJPanel commandJPanel = (CommandModeJPanel) jPanel;
        AddJPanel addJPanel = commandJPanel.getAddJPanel();
        SpaceMarine spMar = addJPanel.getSpaceMarine();
        if (spMar == null) {
            commandJPanel.printError(ConstantsLanguage.INVALID_ARGUMENTS);
            return;
        }
        CommandResult result = caeManager.executeCommand(ConstantsLanguage.REMOVE_LOWER_COMMAND, spMar, null);
        commandJPanel.printMessage(result.getMessageResult());
    }
}
