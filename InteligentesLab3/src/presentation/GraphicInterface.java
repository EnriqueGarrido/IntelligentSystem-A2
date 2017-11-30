package presentation;

import domain.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class GraphicInterface {

	private JFrame frame;
	private JTextField txtSetupPath;
	private JTextField txtMaxDepth;
	private JTextField txtIncrement;
	private JTextField txtOutputPath;
	JLabel lblInfor = new JLabel("");
	JButton btnGo = new JButton("GO!");
	JButton btnSearchOutput = new JButton("Search");
	JComboBox cbStrategy = new JComboBox();
	JCheckBox chbOptimization = new JCheckBox("");
	JComboBox cbOutput = new JComboBox();



	/*******************************************/
	private ArrayList<JFrame> stack = new ArrayList<JFrame>();
	private UninformedSearch uSearch = new UninformedSearch();
	private Problem prob;
	private Strategy strategy;
	private int maxDepth, increment;
	private long timeI, timeF;
	private ArrayList<Node> solList;
	
	/*******************************************/
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphicInterface window = new GraphicInterface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GraphicInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 653, 495);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{262, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{436, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel pnlSettings = new JPanel();
		pnlSettings.setBorder(new TitledBorder(null, "Configuration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlSettings = new GridBagConstraints();
		gbc_pnlSettings.insets = new Insets(0, 0, 5, 5);
		gbc_pnlSettings.fill = GridBagConstraints.BOTH;
		gbc_pnlSettings.gridx = 0;
		gbc_pnlSettings.gridy = 0;
		frame.getContentPane().add(pnlSettings, gbc_pnlSettings);
		GridBagLayout gbl_pnlSettings = new GridBagLayout();
		gbl_pnlSettings.columnWidths = new int[]{289, 0};
		gbl_pnlSettings.rowHeights = new int[]{46, 0, 0, 32, 0, 0, 0, 0, 0, 0};
		gbl_pnlSettings.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_pnlSettings.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlSettings.setLayout(gbl_pnlSettings);
		
		JPanel pnlSetup = new JPanel();
		pnlSetup.setBorder(new TitledBorder(null, "Setup", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlSetup = new GridBagConstraints();
		gbc_pnlSetup.insets = new Insets(0, 0, 5, 0);
		gbc_pnlSetup.anchor = GridBagConstraints.NORTH;
		gbc_pnlSetup.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlSetup.gridx = 0;
		gbc_pnlSetup.gridy = 0;
		pnlSettings.add(pnlSetup, gbc_pnlSetup);
		GridBagLayout gbl_pnlSetup = new GridBagLayout();
		gbl_pnlSetup.columnWidths = new int[]{57, 89, 0};
		gbl_pnlSetup.rowHeights = new int[]{23, 0};
		gbl_pnlSetup.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_pnlSetup.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pnlSetup.setLayout(gbl_pnlSetup);
		
		JButton btnSearchSetup = new JButton("Search");
		btnSearchSetup.addMouseListener(new BtnSearchSetupMouseListener());
		GridBagConstraints gbc_btnSearchSetup = new GridBagConstraints();
		gbc_btnSearchSetup.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSearchSetup.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearchSetup.gridx = 0;
		gbc_btnSearchSetup.gridy = 0;
		pnlSetup.add(btnSearchSetup, gbc_btnSearchSetup);
		
		txtSetupPath = new JTextField();
		txtSetupPath.setEditable(false);
		txtSetupPath.setColumns(10);
		GridBagConstraints gbc_txtSetupPath = new GridBagConstraints();
		gbc_txtSetupPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSetupPath.gridx = 1;
		gbc_txtSetupPath.gridy = 0;
		pnlSetup.add(txtSetupPath, gbc_txtSetupPath);
		
		JPanel pnlAlgorithm = new JPanel();
		pnlAlgorithm.setBorder(new TitledBorder(null, "Algorithm", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlAlgorithm = new GridBagConstraints();
		gbc_pnlAlgorithm.insets = new Insets(0, 0, 5, 0);
		gbc_pnlAlgorithm.fill = GridBagConstraints.BOTH;
		gbc_pnlAlgorithm.gridx = 0;
		gbc_pnlAlgorithm.gridy = 1;
		pnlSettings.add(pnlAlgorithm, gbc_pnlAlgorithm);
		GridBagLayout gbl_pnlAlgorithm = new GridBagLayout();
		gbl_pnlAlgorithm.columnWidths = new int[]{72, 138, 39, 0};
		gbl_pnlAlgorithm.rowHeights = new int[]{26, 0, 0, 0, 0};
		gbl_pnlAlgorithm.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlAlgorithm.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlAlgorithm.setLayout(gbl_pnlAlgorithm);
		
		JLabel lblStrategy = new JLabel("Strategy:");
		GridBagConstraints gbc_lblStrategy = new GridBagConstraints();
		gbc_lblStrategy.anchor = GridBagConstraints.EAST;
		gbc_lblStrategy.insets = new Insets(0, 0, 5, 5);
		gbc_lblStrategy.gridx = 0;
		gbc_lblStrategy.gridy = 0;
		pnlAlgorithm.add(lblStrategy, gbc_lblStrategy);
		cbStrategy.addItemListener(new CbStrategyItemListener());
		
		cbStrategy.setModel(new DefaultComboBoxModel(Strategy.values()));
		GridBagConstraints gbc_cbStrategy = new GridBagConstraints();
		gbc_cbStrategy.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbStrategy.gridwidth = 2;
		gbc_cbStrategy.insets = new Insets(0, 0, 5, 0);
		gbc_cbStrategy.gridx = 1;
		gbc_cbStrategy.gridy = 0;
		pnlAlgorithm.add(cbStrategy, gbc_cbStrategy);
		
		JLabel lblMaxDepth = new JLabel("Max. depth:");
		GridBagConstraints gbc_lblMaxDepth = new GridBagConstraints();
		gbc_lblMaxDepth.anchor = GridBagConstraints.EAST;
		gbc_lblMaxDepth.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxDepth.gridx = 0;
		gbc_lblMaxDepth.gridy = 1;
		pnlAlgorithm.add(lblMaxDepth, gbc_lblMaxDepth);
		
		txtMaxDepth = new JTextField();
		txtMaxDepth.setColumns(10);
		GridBagConstraints gbc_txtMaxDepth = new GridBagConstraints();
		gbc_txtMaxDepth.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMaxDepth.anchor = GridBagConstraints.NORTH;
		gbc_txtMaxDepth.insets = new Insets(0, 0, 5, 5);
		gbc_txtMaxDepth.gridx = 1;
		gbc_txtMaxDepth.gridy = 1;
		pnlAlgorithm.add(txtMaxDepth, gbc_txtMaxDepth);
		
		JLabel lblIncrement = new JLabel("Increment:");
		GridBagConstraints gbc_lblIncrement = new GridBagConstraints();
		gbc_lblIncrement.anchor = GridBagConstraints.EAST;
		gbc_lblIncrement.insets = new Insets(0, 0, 5, 5);
		gbc_lblIncrement.gridx = 0;
		gbc_lblIncrement.gridy = 2;
		pnlAlgorithm.add(lblIncrement, gbc_lblIncrement);
		
		txtIncrement = new JTextField();
		txtIncrement.setEnabled(false);
		txtIncrement.setColumns(10);
		GridBagConstraints gbc_txtIncrement = new GridBagConstraints();
		gbc_txtIncrement.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIncrement.insets = new Insets(0, 0, 5, 5);
		gbc_txtIncrement.gridx = 1;
		gbc_txtIncrement.gridy = 2;
		pnlAlgorithm.add(txtIncrement, gbc_txtIncrement);
		
		JLabel lblOptimization = new JLabel("Optimization:");
		GridBagConstraints gbc_lblOptimization = new GridBagConstraints();
		gbc_lblOptimization.anchor = GridBagConstraints.EAST;
		gbc_lblOptimization.insets = new Insets(0, 0, 0, 5);
		gbc_lblOptimization.gridx = 0;
		gbc_lblOptimization.gridy = 3;
		pnlAlgorithm.add(lblOptimization, gbc_lblOptimization);
		
		chbOptimization.setEnabled(false);
		GridBagConstraints gbc_chbOptimization = new GridBagConstraints();
		gbc_chbOptimization.anchor = GridBagConstraints.WEST;
		gbc_chbOptimization.insets = new Insets(0, 0, 0, 5);
		gbc_chbOptimization.gridx = 1;
		gbc_chbOptimization.gridy = 3;
		pnlAlgorithm.add(chbOptimization, gbc_chbOptimization);
		
		JPanel pnlOutput = new JPanel();
		pnlOutput.setBorder(new TitledBorder(null, "Output File", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlOutput = new GridBagConstraints();
		gbc_pnlOutput.insets = new Insets(0, 0, 5, 0);
		gbc_pnlOutput.fill = GridBagConstraints.BOTH;
		gbc_pnlOutput.gridx = 0;
		gbc_pnlOutput.gridy = 2;
		pnlSettings.add(pnlOutput, gbc_pnlOutput);
		GridBagLayout gbl_pnlOutput = new GridBagLayout();
		gbl_pnlOutput.columnWidths = new int[]{66, 28, 0, 0};
		gbl_pnlOutput.rowHeights = new int[]{20, 0, 0};
		gbl_pnlOutput.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_pnlOutput.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		pnlOutput.setLayout(gbl_pnlOutput);
		cbOutput.addItemListener(new CbOutputItemListener());
		
		cbOutput.setEnabled(false);
		cbOutput.setModel(new DefaultComboBoxModel(new String[] {"Default", "Custom"}));
		cbOutput.setMaximumRowCount(10);
		GridBagConstraints gbc_cbOutput = new GridBagConstraints();
		gbc_cbOutput.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbOutput.anchor = GridBagConstraints.NORTH;
		gbc_cbOutput.gridwidth = 2;
		gbc_cbOutput.insets = new Insets(0, 0, 5, 5);
		gbc_cbOutput.gridx = 0;
		gbc_cbOutput.gridy = 0;
		pnlOutput.add(cbOutput, gbc_cbOutput);
		
		btnSearchOutput.setEnabled(false);
		GridBagConstraints gbc_btnSearchOutput = new GridBagConstraints();
		gbc_btnSearchOutput.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearchOutput.gridx = 0;
		gbc_btnSearchOutput.gridy = 1;
		pnlOutput.add(btnSearchOutput, gbc_btnSearchOutput);
		
		txtOutputPath = new JTextField();
		txtOutputPath.setEditable(false);
		txtOutputPath.setColumns(10);
		GridBagConstraints gbc_txtOutputPath = new GridBagConstraints();
		gbc_txtOutputPath.insets = new Insets(0, 0, 0, 5);
		gbc_txtOutputPath.fill = GridBagConstraints.BOTH;
		gbc_txtOutputPath.gridwidth = 2;
		gbc_txtOutputPath.gridx = 1;
		gbc_txtOutputPath.gridy = 1;
		pnlOutput.add(txtOutputPath, gbc_txtOutputPath);
		btnGo.addMouseListener(new BtnGoMouseListener());
		
		btnGo.setEnabled(false);
		GridBagConstraints gbc_btnGo = new GridBagConstraints();
		gbc_btnGo.insets = new Insets(0, 0, 5, 0);
		gbc_btnGo.fill = GridBagConstraints.VERTICAL;
		gbc_btnGo.anchor = GridBagConstraints.EAST;
		gbc_btnGo.gridx = 0;
		gbc_btnGo.gridy = 3;
		pnlSettings.add(btnGo, gbc_btnGo);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 0;
		frame.getContentPane().add(separator, gbc_separator);
		
		JPanel pnlResult = new JPanel();
		pnlResult.setLayout(null);
		GridBagConstraints gbc_pnlResult = new GridBagConstraints();
		gbc_pnlResult.insets = new Insets(0, 0, 5, 0);
		gbc_pnlResult.fill = GridBagConstraints.BOTH;
		gbc_pnlResult.gridx = 2;
		gbc_pnlResult.gridy = 0;
		frame.getContentPane().add(pnlResult, gbc_pnlResult);
		
		JPanel pnlField = new JPanel();
		pnlField.setBounds(0, 0, 344, 320);
		pnlResult.add(pnlField);
		
		JButton btnLeft = new JButton("<-");
		btnLeft.setEnabled(false);
		btnLeft.setBounds(105, 356, 51, 31);
		pnlResult.add(btnLeft);
		
		JButton btnRight = new JButton("->");
		btnRight.setEnabled(false);
		btnRight.setBounds(187, 356, 51, 31);
		pnlResult.add(btnRight);
		/**********************************************/
		cbStrategy.setEnabled(false);
		txtMaxDepth.setEnabled(false);
		
		/*********************************************/
		
		JButton btnReset = new JButton("Reset");
		btnReset.setEnabled(false);
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.anchor = GridBagConstraints.WEST;
		gbc_btnReset.gridx = 0;
		gbc_btnReset.gridy = 8;
		pnlSettings.add(btnReset, gbc_btnReset);
		
		JPanel pnlInfor = new JPanel();
		pnlInfor.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_pnlInfor = new GridBagConstraints();
		gbc_pnlInfor.gridwidth = 3;
		gbc_pnlInfor.insets = new Insets(0, 0, 0, 5);
		gbc_pnlInfor.fill = GridBagConstraints.BOTH;
		gbc_pnlInfor.gridx = 0;
		gbc_pnlInfor.gridy = 1;
		frame.getContentPane().add(pnlInfor, gbc_pnlInfor);
		pnlInfor.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		pnlInfor.add(lblInfor);
		
		
	}
	private class BtnSearchSetupMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			JFileChooser fileDialog = new JFileChooser();
			int value = fileDialog.showOpenDialog(frame);
			if(value == JFileChooser.APPROVE_OPTION) {
				String file = fileDialog.getSelectedFile().toString();
				txtSetupPath.setText(".."+file.substring(getFileName(file), file.length()));
				prob = new Problem(file);
				cbStrategy.setEnabled(true);
				txtMaxDepth.setEnabled(true);
				chbOptimization.setEnabled(true);
				cbOutput.setEnabled(true);
				btnGo.setEnabled(true);
			}
			
		}
		
	}
	private class CbStrategyItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent arg0) {
			strategy = (Strategy) cbStrategy.getSelectedItem();
			if (strategy == Strategy.IDS)
				txtIncrement.setEnabled(true);
			else {
				txtIncrement.setEnabled(false);
				txtIncrement.setText("");
			}
				
		}
	}
	private class CbOutputItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent arg0) {
			if(cbOutput.getSelectedItem().equals("Default")) {
				btnSearchOutput.setEnabled(false);
			}else {
				btnSearchOutput.setEnabled(true);
			}
		}
	}
	private class BtnGoMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if(checkGo()) {
				lblInfor.setText("");
				algorithm();
			}else {
				lblInfor.setText("Error");
			}
		}
	}
	
	
	/************************************/
	 public int getFileName(String file) {
		 int index =0;
		 for(int i=0; i<file.length(); i++) {
			 if(file.charAt(i) == '\\')
				 index = i;
		 }
		 return index;
	 }
	 public boolean checkGo() {
		 if(cbStrategy.getSelectedItem() != Strategy.IDS
				 && !txtMaxDepth.getText().equals("")) {
			 return true;
		 }else if(cbStrategy.getSelectedItem() ==  Strategy.IDS
				 && !txtMaxDepth.getText().equals("") && !txtIncrement.getText().equals("")) {
			 return true;
		 }else {
			 return false;
		 }
	 }
	 public void algorithm() {
		 maxDepth = Integer.parseInt(txtMaxDepth.getText());
		 if(strategy == Strategy.IDS) increment = Integer.parseInt(txtIncrement.getText());
		 else increment = maxDepth;
		 timeI = System.currentTimeMillis();
		 	solList = uSearch.search(prob, strategy, maxDepth, increment);
		 timeF = System.currentTimeMillis();
		 try {
			writeFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 
	 public void writeFile() throws IOException {
		 BufferedWriter br;
		 File file = new File("OutputGraphic.txt");
			br= new BufferedWriter(new FileWriter(file));
			if(solList!=null) {
				br.write("Strategy: "+strategy.toString());
				br.newLine();
				br.write("n-\t(X, Y)[N, W, E, S]");
				br.newLine();
				for(int i = 0; i<solList.size(); i++) {
					System.out.println(i+1 + "- "+solList.get(solList.size()-i-1).getAction());
					br.write(i+1 + "-\t"+solList.get(solList.size()-i-1).getAction().getActionRepresentation());
					br.newLine();
					//br.write(i+1 + "- "+list.get(list.size()-i-1).getAction());
					//System.out.print("\n");
					//br.write("\n");
					System.out.print(solList.get(solList.size()-i-1).getState().saveMatrix());
					//br.write(list.get(list.size()-i-1).getState().saveMatrix());
					//br.newLine();
					//br.newLine();
				}
				br.write("Cost: " + solList.get(0).getCost());
				br.newLine();
				br.write("Time: "+ (timeF-timeI) + "ms");
				br.newLine();
				br.write("Depth: "+solList.get(0).getDepth());
				br.newLine();
				br.write("Spatial Complexity: " + uSearch.nNodes() + " nodes generated");
				br.newLine();
				br.write("Optimization: "+ (uSearch.getOptimization()? "Yes":"No"));
				br.newLine();
			}else {
				System.out.println("No solution could be found. Check max depth");
				br.write("No solution could be found. Check max depth");
			}
			br.close();
	 }
}

