package hr.fer.zemris.java.hw11.notepad;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Path;

import hr.fer.zemris.java.hw11.notepad.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.notepad.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.notepad.local.AbstractLocalizableAction;
import hr.fer.zemris.java.hw11.notepad.local.LocalizableAction;
import hr.fer.zemris.java.hw11.notepad.local.LocalizationProvider;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.text.BadLocationException;

/**
 * Glavni razred JNotepadPlusPlus.
 * @author Igor Smolkovič
 *
 */
public class JNotepadPlusPlus extends JFrame {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 5569191333846948209L;

	/**
	 * Referenca na lokalizacijski provider prozora.
	 */
	private FormLocalizationProvider flp;

	/**
	 * Referenca na dio koji sadrži tekst.
	 */
	private JTextArea editor;

	/**
	 * Open akcija.
	 */
	private OpenDocumentAction openDocument;

	/**
	 * Save akcija.
	 */
	private SaveDocumentAction saveDocument;

	/**
	 * SaveAs akcija.
	 */
	private SaveAsDocumentAction saveAsDocument;

	/**
	 * Exit akcija.
	 */
	private ExitDocumentAction exitDocument;

	/**
	 * Cut akcija.
	 */
	private CutAction cutAction;

	/**
	 * Copy akcija.
	 */
	private CopyAction copyAction;

	/**
	 * Paste akcija.
	 */
	private PasteAction pasteAction;

	/**
	 * Delete selection akcija.
	 */
	private DeleteSelectionAction deleteAction;

	/**
	 * Zastavica koja označava da je došlo do promjene od zadnjeg spremanja.
	 */
	private boolean fileChanged = false;

	/**
	 * Referenca na datoteku iz koje je pročitano ili se sprema.
	 */
	private File openSaveFile;

	/**
	 * Prostor za spremanje teksta koji se kopira ili cuta.
	 */
	private String clipboard;

	/**
	 * Konstruktor.
	 */
	public JNotepadPlusPlus() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(600, 600);
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		initGUI();
	}

	/**
	 * Metoda koja inicijalizira GUI.
	 */
	private void initGUI() {
		editor = new JTextArea();
		editor.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				setFileChanged(true);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				setFileChanged(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setFileChanged(true);
			}
		});

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(new JScrollPane(editor), BorderLayout.CENTER);

		createActions();
		createMenus();
		createMenus();
		createToolbar();
	}

	/**
	 * Metoda koja dodaje akcije na GUI.
	 */
	private void createActions() {
		openDocument = new OpenDocumentAction("open", flp);
		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O")); 
		saveDocument = new SaveDocumentAction("save", flp);
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveAsDocument = new SaveAsDocumentAction("saveAs", flp);
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift control S"));
		exitDocument = new ExitDocumentAction("exit", flp);
		exitDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));

		cutAction = new CutAction("cut", flp);
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F2"));
		copyAction = new CopyAction("copy", flp);
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F3"));
		pasteAction = new PasteAction("paste", flp);
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F4"));
		deleteAction = new DeleteSelectionAction("deleteSelection", flp);
		deleteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
	}

	/**
	 * Metoda koja dodaje meni na GUI.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu(new LocalizableAction("file", flp));
		fileMenu.add(new JMenuItem(openDocument));
		fileMenu.add(new JMenuItem(saveDocument));
		fileMenu.add(new JMenuItem(saveAsDocument));
		fileMenu.add(new JMenuItem(exitDocument));

		menuBar.add(fileMenu);

		JMenu editMenu = new JMenu(new LocalizableAction("edit", flp));
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));
		editMenu.add(new JMenuItem(deleteAction));

		menuBar.add(editMenu);

		final JMenu languageMenu = new JMenu(new LocalizableAction("language", flp));
		languageMenu.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				languageMenu.removeAll();
				String value = LocalizationProvider.getInstance().getString("allLangs");
				String[] array = value.split(",");
				for (int i = 0; i < array.length; i++) {
					languageMenu.add(new LanguageItem(array[i].trim()));
				}
			}

			@Override
			public void menuDeselected(MenuEvent e) {
			}

			@Override
			public void menuCanceled(MenuEvent e) {
			}
		});
		menuBar.add(languageMenu);
		this.setJMenuBar(menuBar);
	}

	/**
	 * Razred koji omogućava izbor jezika.
	 * @author Igor Smolkovič
	 *
	 */
	@SuppressWarnings("serial")
	private static class LanguageItem extends AbstractAction {
		private String language;

		public LanguageItem(String name) {
			int pos = name.indexOf("-");
			String itemName = name.substring(0, pos);
			putValue(Action.NAME, itemName);
			language = name.substring(pos+1);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage(language);
		}

	}

	/**
	 * Metoda koja stvara toolbar.
	 */
	private void createToolbar() {
		JToolBar toolbar = new JToolBar("Alati");
		toolbar.setFloatable(true);

		toolbar.add(new JButton(openDocument));
		toolbar.addSeparator();
		toolbar.add(new JButton(saveDocument));

		toolbar.addSeparator();
		toolbar.add(new JButton(cutAction));

		toolbar.addSeparator();
		toolbar.add(new JButton(copyAction));

		toolbar.addSeparator();
		toolbar.add(new JButton(pasteAction));

		this.getContentPane().add(toolbar, BorderLayout.PAGE_START);
	}

	/**
	 * Metoda koja radi promjenu stanja da li je text area mijenjan.
	 * @param changed
	 */
	public void setFileChanged(boolean changed) {
		fileChanged = changed;
	}

	/**
	 * Metoda koja otvara dijalog za spremanje.
	 * @param saveAs
	 */
	public void showSaveFileDialog(boolean saveAs) {
		if (saveAs) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(LocalizationProvider.getInstance().getString("saveAsTitle"));
			if(fc.showSaveDialog(JNotepadPlusPlus.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File saveAsLocation = fc.getSelectedFile();
			boolean writeComplete = JNotepadFile.saveToFile(editor.getText(), saveAsLocation);
			if (!writeComplete) {
				JOptionPane.showMessageDialog(
						JNotepadPlusPlus.this,
						LocalizationProvider.getInstance().getString("errorSave"),
						LocalizationProvider.getInstance().getString("error"),
						JOptionPane.ERROR_MESSAGE
				);
				return;
			}
			setFileChanged(false);
			return;
		} else if (openSaveFile == null) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(LocalizationProvider.getInstance().getString("saveTitle"));
			if(fc.showSaveDialog(JNotepadPlusPlus.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			openSaveFile = fc.getSelectedFile();
		}
		boolean writeComplete = JNotepadFile.saveToFile(editor.getText(), openSaveFile);
		if (!writeComplete) {
			JOptionPane.showMessageDialog(
					JNotepadPlusPlus.this,
					LocalizationProvider.getInstance().getString("errorSave"),
					LocalizationProvider.getInstance().getString("error"),
					JOptionPane.ERROR_MESSAGE
			);
			return;
		}
		setFileChanged(false);
	}

	/**
	 * Metoda koja otvara dijalog za učitavanje.
	 */
	public void showOpenFileDialog() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(LocalizationProvider.getInstance().getString("openTitle"));
		if(fc.showOpenDialog(JNotepadPlusPlus.this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		String text = JNotepadFile.readFromFile(filePath);
		if (text == null) {
			JOptionPane.showMessageDialog(
					JNotepadPlusPlus.this,
					LocalizationProvider.getInstance().getString("errorRead"),
					LocalizationProvider.getInstance().getString("error"),
					JOptionPane.ERROR_MESSAGE
			);
			return;
		}
		editor.setText(text);
	}

	/**
	 * Razred koji implementira open document opciju.
	 * @author Igor Smolkovič
	 *
	 */
	@SuppressWarnings("serial")
	private class OpenDocumentAction extends AbstractLocalizableAction {
		public OpenDocumentAction(String key, ILocalizationProvider lp) {
			super(key, lp);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (fileChanged) {
				showSaveFileDialog(false);
			}
			showOpenFileDialog();
		}
	}

	/**
	 * Razred koji implementira save opciju.
	 * @author Igor Smolkovič
	 *
	 */
	@SuppressWarnings("serial")
	private class SaveDocumentAction extends AbstractLocalizableAction {
		public SaveDocumentAction(String key, ILocalizationProvider lp) {
			super(key, lp);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!fileChanged) { return; }
			showSaveFileDialog(false);
		}
	}

	/**
	 * Razred koji implementira saveAs opciju.
	 * @author Igor Smolkovič
	 *
	 */
	@SuppressWarnings("serial")
	private class SaveAsDocumentAction extends AbstractLocalizableAction {
		public SaveAsDocumentAction(String key, ILocalizationProvider lp) {
			super(key, lp);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			showSaveFileDialog(true);
		}
	}

	/**
	 * Razred koji implementira exit opciju.
	 * @author Igor Smolkovič
	 *
	 */
	@SuppressWarnings("serial")
	private class ExitDocumentAction extends AbstractLocalizableAction {
		public ExitDocumentAction(String key, ILocalizationProvider lp) {
			super(key, lp);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (fileChanged ) {
				showSaveFileDialog(false);
			}
			System.exit(0);
		}
	}

	/**
	 * Razred koji implementira cut opciju.
	 * @author Igor Smolkovič
	 *
	 */
	@SuppressWarnings("serial")
	private class CutAction extends AbstractLocalizableAction {
		public CutAction(String key, ILocalizationProvider lp) {
			super(key, lp);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			copyCutRemoveSelected(EditType.CUT);
		}
	}

	/**
	 * Razred koji implementira copy opciju.
	 * @author Igor Smolkovič
	 *
	 */
	@SuppressWarnings("serial")
	private class CopyAction extends AbstractLocalizableAction {
		public CopyAction(String key, ILocalizationProvider lp) {
			super(key, lp);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			copyCutRemoveSelected(EditType.COPY);
		}
	}

	/**
	 * Razred koji implementira paste opciju.
	 * @author Igor Smolkovič
	 *
	 */
	@SuppressWarnings("serial")
	private class PasteAction extends AbstractLocalizableAction {
		public PasteAction(String key, ILocalizationProvider lp) {
			super(key, lp);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				editor.insert(clipboard, editor.getCaretPosition());
			} catch (IllegalArgumentException ignorable) { }
		}
	}

	/**
	 * Razred koji implementira delete selection opciju.
	 * @author Igor Smolkovič
	 *
	 */
	@SuppressWarnings("serial")
	private class DeleteSelectionAction extends AbstractLocalizableAction {
		public DeleteSelectionAction(String key, ILocalizationProvider lp) {
			super(key, lp);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			copyCutRemoveSelected(EditType.REMOVE);
		}
	}

	/**
	 * Metoda koja implementira akcije copy, cut te delete selection.
	 * @param type
	 */
	private void copyCutRemoveSelected(EditType type) {
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		if(len == 0) return;
		int offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
		try {
			if (type == EditType.COPY || type == EditType.CUT){
				clipboard = editor.getDocument().getText(offset, len);
			} 
			if (type == EditType.REMOVE || type == EditType.CUT) {
				editor.getDocument().remove(offset, len);
			}
		} catch (BadLocationException ignorable) { }
	}

	/**
	 * Metoda koja se pokreće prilikom pokretanja programa.
	 * @param args argumenti komandne linije. Ne koriste se.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JNotepadPlusPlus().setVisible(true);
			}
		});
	}
}
