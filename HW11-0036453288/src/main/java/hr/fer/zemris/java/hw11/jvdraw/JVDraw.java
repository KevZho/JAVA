package hr.fer.zemris.java.hw11.jvdraw;

import hr.fer.zemris.java.hw11.jvdraw.command.CircleCommand;
import hr.fer.zemris.java.hw11.jvdraw.command.FilledCircleCommand;
import hr.fer.zemris.java.hw11.jvdraw.command.LineCommand;
import hr.fer.zemris.java.hw11.jvdraw.component.JColorArea;
import hr.fer.zemris.java.hw11.jvdraw.component.JDrawingCanvas;
import hr.fer.zemris.java.hw11.jvdraw.component.JVDLabel;
import hr.fer.zemris.java.hw11.jvdraw.go.BoundingBox;
import hr.fer.zemris.java.hw11.jvdraw.go.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.interfaces.DrawingModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

/**
 * Glavni program koji inicijaliza gui postavlja glavne ActionListenere za GUI.
 * @author Igor Smolkovič
 *
 */
public class JVDraw extends JFrame {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 3864156777948534492L;

	/**
	 * Boja crte.
	 */
	private JColorArea foregroundColor;

	/**
	 * Boja ispune.
	 */
	private JColorArea backgroundColor;

	/**
	 * Referenca na prostor za crtanje.
	 */
	private JDrawingCanvas canvas;

	/**
	 * Referenca na DrawingModel.
	 */
	private DrawingModel model;

	/**
	 * Referenca na jList koji prikazuje objekte.
	 */
	private JList<GeometricalObject> objectList;

	/**
	 * Referenca na model da za jList.
	 */
	private DrawingObjectListModel listModel;

	/**
	 * Referenca na scrollPane za jlist.
	 */
	private JScrollPane scrollPane;

	/**
	 * Referenca na datoteku u koju se sprema model. Setirano je u slučaju da je korisnik
	 * već jednom spremio.
	 */
	private File saveFile;


	/**
	 * Konstruktor.
	 */
	public JVDraw() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(600, 600);
		initGUI();
	}

	/**
	 * Inicijalizacija GUI-a.
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());

		addMenuBar();
		addToolbar();

		addCanvas();
		addList();

		addBottomComponent();
	}

	/**
	 * Metoda koja dodaje listu na GUI.
	 */
	private void addList() {
		listModel = new DrawingObjectListModel(model);
		model.addDrawingModelListener(listModel);
		objectList = new JList<GeometricalObject>(listModel);

		objectList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = objectList.getSelectedIndex();
					model.updateObject(index);
					objectList.clearSelection();
				}
			}
		});

		scrollPane = new JScrollPane(objectList);
		scrollPane.setPreferredSize(new Dimension(180, 500));
		getContentPane().add(scrollPane, BorderLayout.EAST);
	}

	/**
	 * Metoda koja dodaje prostor za crtanje na GUI.
	 */
	private void addCanvas() {
		/**
		 * Kreiramo novi model koji pamti objekte za crtanje.
		 */

		model = new DrawingModelImpl();
		canvas = new JDrawingCanvas(foregroundColor, backgroundColor, model);
		/**
		 * Treba postaviti canvas kao listener od modela.
		 */
		model.addDrawingModelListener(canvas);
		getContentPane().add(canvas, BorderLayout.CENTER);
	}

	/**
	 * Metoda koja dodaje labelu na dnu na GUI.
	 */
	private void addBottomComponent() {
		JPanel panel = new JPanel();
		JVDLabel label = new JVDLabel(foregroundColor, backgroundColor);
		backgroundColor.addColorChangeListener(label);
		foregroundColor.addColorChangeListener(label);
		panel.add(label);
		getContentPane().add(panel, BorderLayout.SOUTH);
	}

	/**
	 * Metoda koja dodaje toolbar na GUI.
	 */
	private void addToolbar() {
		JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
		toolbar.setFloatable(false);
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));

		foregroundColor = new JColorArea(Color.RED);
		backgroundColor = new JColorArea(Color.BLUE);

		toolbar.add(foregroundColor);
		toolbar.add(backgroundColor);

		ButtonGroup btGroup = new ButtonGroup();
		JButton btLine = new JButton("Line");
		btLine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setCommand(new LineCommand());
			}
		});

		JButton btCircle = new JButton("Circle");
		btCircle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setCommand(new CircleCommand());
			}
		});

		JButton btFilledCircle = new JButton("Filled circle");
		btFilledCircle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setCommand(new FilledCircleCommand());
			}
		});

		btGroup.add(btLine);
		btGroup.add(btCircle);
		btGroup.add(btFilledCircle);

		toolbar.addSeparator();
		toolbar.add(btLine);
		toolbar.add(btCircle);
		toolbar.add(btFilledCircle);

		getContentPane().add(toolbar, BorderLayout.NORTH);
	}

	/**
	 * Metoda koja dodaje menu na GUI.
	 */
	private void addMenuBar() {
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (canvas.isCanvasChanged()) {
					showSaveDialog(saveFile != null, "Želite li spremiti prije otvaranja novog modela?");
				}
				JVDFile.openFile(model);
				saveFile = null;
				canvas.setCanvasChanged(false);
			}
		});
		menu.add(open);

		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (saveFile == null) {
					saveFile = JVDFile.getSaveFile();
				}
				if (saveFile == null) { return; }
				JVDFile.saveModelToFile(saveFile, model);
				canvas.setCanvasChanged(false);
			}
		});
		menu.add(save);

		JMenuItem saveAs = new JMenuItem("SaveAs");
		saveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File saveLocation = JVDFile.getSaveFile();
				if (saveLocation == null) { return; }
				JVDFile.saveModelToFile(saveLocation, model);
				canvas.setCanvasChanged(false);
			}
		});
		menu.add(saveAs);

		JMenuItem export = new JMenuItem("Export");
		export.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JVDFile.exportModel(model, new BoundingBox(canvas.getWidth(), canvas.getHeight(), 0, 0));
			}
		});
		menu.add(export);

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (canvas.isCanvasChanged() == true) {
					showSaveDialog(saveFile != null, "Želite li spremiti prije zatvaranja aplikacije?");
				}
				System.exit(0);
			}
		});
		menu.add(exit);

		bar.add(menu);
		setJMenuBar(bar);
	}

	/**
	 * Metoda koja otvara saveDialog prilikom izlaska iz programa ili pokušaja otvaranje
	 * novog modela. U tim slučajevima potrebno je spremiti model te se moraju prikazati dva
	 * tipa poruka. Jedna pretpostavlja da je model već bio spreman pa samo pita da li želimo
	 * spremiti u postojeću datoteku, a druga pita za novu lokaciju datoteke i sprema u nju.
	 * @param alreadySaved ako je već bilo spremano.
	 * @param text text koji se prikazuje u messageboxu.
	 */
	public void showSaveDialog(boolean alreadySaved, String text) {
		if (alreadySaved) {
			int status = JOptionPane.showConfirmDialog(this, text);
			if (status == JOptionPane.OK_OPTION) {
				JVDFile.saveModelToFile(saveFile, model);
			}
			return;
		}
		saveFile = JVDFile.getSaveFile();
		if (saveFile != null) {
			JVDFile.saveModelToFile(saveFile, model);
		}
	}

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * @param args argumenti komandne linije. Ne koriste se.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JVDraw().setVisible(true);
			}
		});
	}
}
