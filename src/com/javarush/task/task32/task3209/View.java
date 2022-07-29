package com.javarush.task.task32.task3209;

import com.javarush.task.task32.task3209.listeners.FrameListener;
import com.javarush.task.task32.task3209.listeners.TabbedPaneChangeListener;
import com.javarush.task.task32.task3209.listeners.UndoListener;

import javax.swing.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {
    private Controller controller;
    private UndoManager undoManager = new UndoManager();
    private UndoListener undoListener = new UndoListener(undoManager);

    private JTabbedPane tabbedPane = new JTabbedPane();
    private JTextPane htmlTextPane = new JTextPane();
    private JEditorPane plainTextPane = new JEditorPane();

    public UndoListener getUndoListener() {
        return undoListener;
    }

    public View() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            ExceptionHandler.log(e);
        }
        SwingUtilities.updateComponentTreeUI(tabbedPane);
        SwingUtilities.updateComponentTreeUI(htmlTextPane);
        SwingUtilities.updateComponentTreeUI(plainTextPane);
    }

    public Controller getController() {
        return controller;
    }

    public void init() {
        initGui();
        FrameListener listener = new FrameListener(this);
        addWindowListener(listener);
        setVisible(true);
    }

    public void initGui() {
        initMenuBar();
        initEditor();
        pack();
    }

    public boolean isHtmlTabSelected() {
        return tabbedPane.getSelectedIndex() == 0;
//        Он должен возвращать true, если выбрана вкладка, отображающая html в
//        панели вкладок (подсказка: ее индекс 0).
    }

    public void resetUndo() {
        undoManager.discardAllEdits();
    }

    public void undo() {
        try {
            undoManager.undo();
        } catch (CannotUndoException e) {
            ExceptionHandler.log(e);
        }
    }

    public void redo() {
        try {
            undoManager.redo();
        } catch (CannotRedoException e) {
            ExceptionHandler.log(e);
        }
    }

    public void selectHtmlTab() {
        tabbedPane.setSelectedIndex(0);
        resetUndo();
    }


    public void exit() {
        controller.exit();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        switch (command) {
            case "Новый":
                controller.createNewDocument();
                break;

            case "Открыть":
                controller.openDocument();
                break;

            case "Сохранить":
                controller.saveDocument();
                break;

            case "Сохранить как...":
                controller.saveDocumentAs();
                break;

            case "Выход":
                controller.exit();
                break;

            case "О программе":
                showAbout();
                break;
        }
    }

    public void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        MenuHelper.initFileMenu(this, menuBar);
        MenuHelper.initEditMenu(this, menuBar);
        MenuHelper.initStyleMenu(this, menuBar);
        MenuHelper.initAlignMenu(this, menuBar);
        MenuHelper.initColorMenu(this, menuBar);
        MenuHelper.initFontMenu(this, menuBar);
        MenuHelper.initHelpMenu(this, menuBar);

        getContentPane().add(menuBar, BorderLayout.NORTH);
    }

    public void initEditor() {
//6.1. Устанавливать значение  в качестве типа контента для компонента htmlTextPane.
//Найди и используй подходящий метод.
        htmlTextPane.setContentType("text/html");
//6.2. Создавать новый локальный компонент JScrollPane на базе htmlTextPane.
        JScrollPane localHtmlTP = new JScrollPane(htmlTextPane);
//6.3. Добавлять вкладку в панель tabbedPane с именем "HTML" и компонентом из предыдущего пункта.
        tabbedPane.add("HTML", localHtmlTP);
//6.4. Создавать новый локальный компонент JScrollPane на базе plainTextPane.
        JScrollPane localPlainTP = new JScrollPane(plainTextPane);
//6.5. Добавлять еще одну вкладку в tabbedPane с именем "Текст" и компонентом из предыдущего пункта.
        tabbedPane.add("Текст", localPlainTP);
//6.6. Устанавливать предпочтительный размер панели tabbedPane.
        tabbedPane.setPreferredSize(new Dimension());
//6.7. Создавать объект класса TabbedPaneChangeListener и устанавливать его в качестве слушателя изменений в tabbedPane.
        TabbedPaneChangeListener listener = new TabbedPaneChangeListener(this);
        tabbedPane.addChangeListener(listener);
//6.8. Добавлять по центру панели контента текущего фрейма нашу панель с вкладками.
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    public boolean canUndo() {
        return undoManager.canUndo();
    }

    public boolean canRedo() {
        return undoManager.canRedo();
    }

    public void update() {
        htmlTextPane.setDocument(controller.getDocument());
    }

    public void showAbout() {
        JOptionPane.showMessageDialog(this,
                "Самый непонятный проект. HTML редактор вроде как",
                "О программе",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void selectedTabChanged() {
        switch (tabbedPane.getSelectedIndex()) {
            case 0:
                controller.setPlainText(plainTextPane.getText());
                break;
            case 1:
                plainTextPane.setText(controller.getPlainText());
                break;
        }
        resetUndo();
    }
}
