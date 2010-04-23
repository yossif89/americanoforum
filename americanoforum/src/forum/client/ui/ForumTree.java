package forum.client.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import forum.client.controllerlayer.ControllerHandler;
import forum.client.controllerlayer.ControllerHandlerFactory;
import forum.client.controllerlayer.ControllerHandlerImpl;
import forum.tcpcommunicationlayer.AddMessageMessage;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Handler;
import javax.swing.BoxLayout;
import javax.swing.SpringLayout;

/**
 * @author Tomer Heber
 *
 */
public class ForumTree implements ForumTreeHandler {
	
	/**
	 * The JTree GUI component.
	 */
	private JTree m_tree;
	
	/**
	 * The JPanel GUI component.
	 */
	private JPanel m_panel;

        private JButton  registerButton,loginButton,logoffButton;
	/**
	 * A pipe interface to communicate with the controller layer.
	 */
	private ControllerHandler m_pipe;
	
	/**
	 * A thread pool that is used to initiate operations in the controller layer.
	 */
	private ExecutorService m_pool = Executors.newCachedThreadPool();
	
	public ForumTree() {				
		UIManager.put("Tree.collapsedIcon", new ImageIcon("./images/plus-8.png"));
		UIManager.put("Tree.expandedIcon", new ImageIcon("./images/minus-8.png"));
		
		m_pipe = ControllerHandlerFactory.getPipe();
            //    m_pipe = new ControllerHandlerImpl();
		/* Add an observer to the controller (The observable). */
		m_pipe.addObserver(new ForumTreeObserver(this));
		
		m_tree = new JTree();
		m_tree.putClientProperty("JTree.lineStyle", "None");		

                 String ans = m_pipe.getForumView();
                  refreshForum(ans);

       
		
		
		ForumTreeCellRenderer renderer = new ForumTreeCellRenderer(this);
		m_tree.setCellRenderer(renderer);
		m_tree.setCellEditor(new ForumTreeCellEditor(renderer));
		m_tree.setEditable(true);
		
		m_tree.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				BasicTreeUI ui = (BasicTreeUI) m_tree.getUI();
				ui.setLeftChildIndent(ui.getLeftChildIndent());
				ui.setRightChildIndent(ui.getRightChildIndent());
			}
		});
		
		m_panel = new JPanel();
                JPanel temp = new JPanel();
                temp.setBackground(Color.WHITE);
		m_panel.setBackground(Color.WHITE);
		JScrollPane pane = new JScrollPane(m_tree);
		pane.setPreferredSize(new Dimension(610,435));
		registerButton = new JButton("Register");
                 loginButton = new JButton("Login");
                logoffButton = new JButton("Logoff");
		temp.add(registerButton);
                temp.add(loginButton);
                temp.add(logoffButton);

                registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
                                 java.awt.EventQueue.invokeLater(new Runnable() {
                                 public void run() {
                                           JPanel x =  new RegistrationForm(m_pipe,registerButton);
                                           JFrame y = new JFrame();
                                           y.setSize(new Dimension(500, 350));
                                           y.add(x);
                                           x.setVisible(true);
                                             y.setVisible(true);
                                     }
                                    });
			}
		});

                loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
                                 java.awt.EventQueue.invokeLater(new Runnable() {
                                 public void run() {
                                           JFrame x =  new LoginFrame(m_pipe,loginButton);
                                           x.setVisible(true);
                                     }
                                    });
			}
		});



                m_panel.add(temp);
                m_panel.add(pane);
		m_panel.setPreferredSize(new Dimension(620,460));
	}
	
	/**
	 * 
	 * @return The forum tree GUI component. 
	 */
	public Component getForumTreeUI() {
		return m_panel;
	}
	
	@Override
	public synchronized void refreshForum(String encodedView) {
                System.out.println("encoded \n"+ encodedView);
		ForumCell rootCell = decodeView(encodedView);
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootCell); 
		
		Stack<DefaultMutableTreeNode> stack = new Stack<DefaultMutableTreeNode>();
		stack.add(rootNode);
			
		while (!stack.isEmpty()) {
			DefaultMutableTreeNode node = stack.pop();
			ForumCell cell = (ForumCell)(node.getUserObject());
			for (ForumCell sonCell : cell.getSons()) {
				DefaultMutableTreeNode sonNode = new DefaultMutableTreeNode(sonCell);
				node.add(sonNode);
				stack.add(sonNode);
			}		
		}
		
		DefaultTreeModel model = new DefaultTreeModel(rootNode);
		m_tree.setModel(model);	
		for (int i = 0; i < m_tree.getRowCount(); i++) {
	         m_tree.expandRow(i);
		}

	}
	
	@Override
	public void NotifyError(String errorMessage) {
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame,
			    errorMessage,
			    "Operation failed.",
			    JOptionPane.WARNING_MESSAGE);
	}

        public ForumCell  getDataFromLine(String line){
            ForumCell toRet=null;
               System.out.println("line "+line);
               StringTokenizer tempTok = new StringTokenizer(line, "$$");
               String num = tempTok.nextToken();
               System.out.println("next token"+num);
               long id = Long.parseLong(num);
               String subject = tempTok.nextToken();
               System.out.println("suBjEct= "+subject);
                 String cont="";
               if (tempTok.hasMoreTokens())
                    cont = tempTok.nextToken();
                 String username="";
               if (tempTok.hasMoreTokens())
                    username = tempTok.nextToken();

                   System.out.println("ConTent= "+cont);
               toRet = new ForumCell(id, username, subject, cont);
                return  toRet;
        }

	/**
	 * Receives an encoding describing the forum tree.<br>
	 * It decodes the description and returns the tree representation in a ForumCell instance.
	 * 
	 * @return The tree representing the forum.
	 */
	private ForumCell decodeView(String encodedView) {
            HashMap<Long,ForumCell> mapping = new HashMap<Long, ForumCell>();
            ForumCell toRet=new ForumCell(-2,"666666666666","66666666666","66666666666666666");
             ForumCell temp;
            StringTokenizer lineTok = new StringTokenizer(encodedView,"\n");
            lineTok.nextToken();
              StringTokenizer tempTok;
            while(lineTok.hasMoreTokens()){

                String line = lineTok.nextToken();
                System.out.println("aaaaaaaaaa line = "+line);
                tempTok = new StringTokenizer(line, "$$");
                String first = tempTok.nextToken();
                if(first.indexOf(",")<=0){
                        temp = getDataFromLine(line);
                        toRet.add(temp);
                        mapping.put(temp.getId(), temp);
                }
                else{
                        temp = getDataFromLine(line.substring(line.indexOf(",")+1));
                        tempTok = new StringTokenizer(first, ",");
                        long fatherId = Long.parseLong(tempTok.nextToken());
                        mapping.get(fatherId).add(temp);
                        mapping.put(temp.getId(), temp);
                }
            }

            return toRet;
	}

	/**
	 * Modifies a message, and updates the forum accordingly.
	 * 
	 * @param newContent The new content of the message.
	 */
	public void modifyMessage(final String newContent, final JButton button) {
		button.setEnabled(false);
		m_pool.execute(new Runnable() {
			@Override
			public void run() {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)m_tree.getSelectionPath().getLastPathComponent();
				ForumCell cell = (ForumCell) node.getUserObject();				
				m_pipe.modifyMessage(cell.getId(),newContent,button);				
			}
		});
	}

        /**
	 * Modifies a message, and updates the forum accordingly.
	 *
	 * @param newContent The new content of the message.
	 */
	public void Register(final String username,final String password,final String first,final String last,final String email,final String address,final String gender, final JButton button) {
		button.setEnabled(false);
		m_pool.execute(new Runnable() {
			@Override
			public void run() {
				m_pipe.register(username, password, first, last, email, address, gender, button);
			}
		});
	}
	
	/**
	 * Replies to the selected message.
	 */
	public void replyToMessage(final String subj,final String cont,final JButton button) {
		button.setEnabled(false);
		m_pool.execute(new Runnable() {
			
                    @Override
                    public void run() {
                            DefaultMutableTreeNode node = (DefaultMutableTreeNode)m_tree.getSelectionPath().getLastPathComponent();
                            ForumCell cell = (ForumCell) node.getUserObject();
                            m_pipe.addReplyToMessage(cell.getId(),subj,cont,button);
                    }
		});			
	}

	/**
	 * Deletes the selected message.
	 */
	public void deleteMessage(final JButton button) {
		button.setEnabled(false);
		m_pool.execute(new Runnable() {
			
			@Override
			public void run() {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)m_tree.getSelectionPath().getLastPathComponent();
				ForumCell cell = (ForumCell) node.getUserObject();				
				m_pipe.deleteMessage(cell.getId(),button);			
			}
		});		
	}

	/**
	 * Adds a new message.
	 */
	public void addNewMessage(final String subj,final String cont, final JButton button) {
		button.setEnabled(false);
		m_pool.execute(new Runnable() {
			
			@Override
			public void run() {				
				m_pipe.addNewMessage(subj,cont,button);
			}
		});
	}
	
	/**
	 * This is for testing purposes only! <br>
	 * Delete when done testing!
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ForumTree tree = new ForumTree();
		
		JFrame frame = new JFrame("test");
		frame.setSize(new Dimension(640,480));
		
		frame.getContentPane().add(tree.getForumTreeUI());	
		frame.setVisible(true);		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
