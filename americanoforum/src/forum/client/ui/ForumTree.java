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
import java.util.Vector;
import java.util.logging.Handler;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
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

        private  StatusPanel statusPanel;

        private JButton  registerButton,loginButton,logoffButton,searchButton,promoteButton;
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
                statusPanel = new StatusPanel();
                 String ans = m_pipe.getForumView("null");
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
                searchButton = new JButton("Search");
                promoteButton = new JButton("Promote");
                registerButton.setForeground(Color.GRAY);
                registerButton.setOpaque(false);
                registerButton.setFocusPainted(false);
                loginButton.setForeground(Color.GRAY);
                loginButton.setOpaque(false);
                loginButton.setFocusPainted(false);
                logoffButton.setForeground(Color.GRAY);
                logoffButton.setOpaque(false);
                logoffButton.setFocusPainted(false);
                searchButton.setForeground(Color.GRAY);
                searchButton.setOpaque(false);
                searchButton.setFocusPainted(false);
                promoteButton.setForeground(Color.GRAY);
                promoteButton.setOpaque(false);
                promoteButton.setFocusPainted(false);
		temp.add(registerButton);
                temp.add(loginButton);
                temp.add(logoffButton);
                temp.add(searchButton);
                temp.add(promoteButton);

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

                logoffButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
                                 java.awt.EventQueue.invokeLater(new Runnable() {
                                 public void run() {
                                        m_pipe.logoff(logoffButton);
                                     }
                                    });
			}
		});

                searchButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e){
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                JFrame x =  new SearchFrame(m_pipe,searchButton);
                                x.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                                x.setVisible(true);
                            }
                        });
                    }
                });

                promoteButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e){
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run(){
                                promoteButton.setEnabled(false);
                                m_pipe.showUsersToPromote(promoteButton);
                            }
                        });
                    }
                });

                m_panel.add(temp);
                pane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 102)));
                m_panel.add(pane);
                m_panel.add(statusPanel);
		m_panel.setPreferredSize(new Dimension(620,460));
	}

        public ForumTree(JTree searchResultsTree) { //used for search results only!
		UIManager.put("Tree.collapsedIcon", new ImageIcon("./images/plus-8.png"));
		UIManager.put("Tree.expandedIcon", new ImageIcon("./images/minus-8.png"));

		m_pipe = null;
            //    m_pipe = new ControllerHandlerImpl();
		/* Add an observer to the controller (The observable). */
		//m_pipe.addObserver(new ForumTreeObserver(this));

		m_tree = searchResultsTree;
		m_tree.putClientProperty("JTree.lineStyle", "None");
                //statusPanel = new StatusPanel();
                 //String ans = m_pipe.getForumView("null");
                 //refreshForum(ans);

		SearchForumTreeCellRenderer renderer = new SearchForumTreeCellRenderer(this);
		m_tree.setCellRenderer(renderer);
		m_tree.setCellEditor(new SearchForumTreeCellEditor(renderer));
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
                //m_panel.add(label);
		JScrollPane pane = new JScrollPane(m_tree);
		pane.setPreferredSize(new Dimension(610,435));
//		registerButton = new JButton("Register");
//                 loginButton = new JButton("Login");
//                logoffButton = new JButton("Logoff");
//                searchButton = new JButton("Search");
//                registerButton.setForeground(Color.GRAY);
//                registerButton.setOpaque(false);
//                registerButton.setFocusPainted(false);
//                loginButton.setForeground(Color.GRAY);
//                loginButton.setOpaque(false);
//                loginButton.setFocusPainted(false);
//                logoffButton.setForeground(Color.GRAY);
//                logoffButton.setOpaque(false);
//                logoffButton.setFocusPainted(false);
//                searchButton.setForeground(Color.GRAY);
//                searchButton.setOpaque(false);
//                searchButton.setFocusPainted(false);
//		temp.add(registerButton);
//                temp.add(loginButton);
//                temp.add(logoffButton);
//                temp.add(searchButton);

//                registerButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//                                 java.awt.EventQueue.invokeLater(new Runnable() {
//                                 public void run() {
//                                           JPanel x =  new RegistrationForm(m_pipe,registerButton);
//                                           JFrame y = new JFrame();
//                                           y.setSize(new Dimension(500, 350));
//                                           y.add(x);
//                                           x.setVisible(true);
//                                             y.setVisible(true);
//                                     }
//                                    });
//			}
//		});
//
//                loginButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//                                 java.awt.EventQueue.invokeLater(new Runnable() {
//                                 public void run() {
//                                           JFrame x =  new LoginFrame(m_pipe,loginButton);
//                                           x.setVisible(true);
//                                     }
//                                    });
//			}
//		});
//
//                logoffButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//                                 java.awt.EventQueue.invokeLater(new Runnable() {
//                                 public void run() {
//                                        m_pipe.logoff(logoffButton);
//                                     }
//                                    });
//			}
//		});
//
//                searchButton.addActionListener(new ActionListener() {
//
//                    @Override
//                    public void actionPerformed(ActionEvent e){
//                        java.awt.EventQueue.invokeLater(new Runnable() {
//                            public void run() {
//                                JFrame x =  new SearchFrame(m_pipe,searchButton);
//                                x.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//                                x.setVisible(true);
//                            }
//                        });
//                    }
//                });
//
//                m_panel.add(temp);
                pane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 102)));
                m_panel.add(pane);
                //m_panel.add(statusPanel);
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
               StringTokenizer tempTok = new StringTokenizer(line, "$$");
               String num = tempTok.nextToken();
               long id = Long.parseLong(num);
               String subject = tempTok.nextToken();
               String cont = "";
               if (tempTok.hasMoreTokens())
                    cont = tempTok.nextToken();
                 String username="";
               if (tempTok.hasMoreTokens())
                    username = tempTok.nextToken();
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
            System.out.println("encoded view: "+encodedView);
            HashMap<Long,ForumCell> mapping = new HashMap<Long, ForumCell>();
            ForumCell toRet=new ForumCell(-2,"666666666666","66666666666","66666666666666666");
             ForumCell temp;
            StringTokenizer lineTok = new StringTokenizer(encodedView,"\n");
            
            statusPanel.setSize(610,statusPanel.getHeight());
            
            this.statusPanel.get_Name().setText(lineTok.nextToken());
            this.statusPanel.get_Size().setText("There are "+lineTok.nextToken()+" online users:");
            String users = lineTok.nextToken();
            StringTokenizer psikTok = new StringTokenizer(users,",");
            int counter =0;
            String newUsers="";
            while (psikTok.hasMoreTokens()){
                if (counter>7){
                    counter=0;
                    newUsers+="\n";
                }
                newUsers+=psikTok.nextToken()+",";
                counter++;
            }
            this.statusPanel.get_Online().setText(newUsers.substring(0,newUsers.length()-1));

              StringTokenizer tempTok;
            while(lineTok.hasMoreTokens()){

                String line = lineTok.nextToken();
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

        private ForumCell decodeSearchResults(String decRes){
            HashMap<Long,ForumCell> mapping = new HashMap<Long, ForumCell>();
            ForumCell toRet=new ForumCell(-2,"666666666666","66666666666","66666666666666666");
             ForumCell temp;
            StringTokenizer lineTok = new StringTokenizer(decRes,"\n");
            while(lineTok.hasMoreTokens()){
                String msg = lineTok.nextToken();
                StringTokenizer dollarTok = new StringTokenizer(msg,"$$");
                String subj = "NO SUBJECT";
                String cont = "NO CONTENT";
                String username = "";
                long msg_id = -1;
                if(dollarTok.hasMoreTokens()){
                    String id=dollarTok.nextToken();
                    msg_id = Long.parseLong(id);
                }
                if(dollarTok.hasMoreTokens())
                    subj = dollarTok.nextToken();
                if(dollarTok.hasMoreTokens())
                    cont = dollarTok.nextToken();
                if(dollarTok.hasMoreTokens())
                    username = dollarTok.nextToken();
                temp = new ForumCell(msg_id, username, subj, cont);
                toRet.add(temp);
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
		
		JFrame frame = new JFrame("Americano Forum");
		frame.setSize(new Dimension(800,700));
		frame.setResizable(false);
                frame.setLocation(300, 15);
		frame.getContentPane().add(tree.getForumTreeUI());	
		frame.setVisible(true);		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

    public void showSearchResults(String encodedResults) {
        ForumCell ans = decodeSearchResults(encodedResults);
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(ans);

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
	//m_tree.setModel(model);
	//for (int i = 0; i < m_tree.getRowCount(); i++) {
	//        m_tree.expandRow(i);
	//}
 /*       forum resultTree = new JTree();
        resultTree.putClientProperty("JTree.lineStyle", "None");
        //ForumTreeCellRenderer renderer = new ForumTreeCellRenderer();
        resultTree.setCellRenderer(renderer);
	resultTree.setCellEditor(new ForumTreeCellEditor(renderer));
	resultTree.setEditable(true);
        resultTree.setModel(model);


        JFrame searchResultsFrame = new JFrame("Results");
        searchResultsFrame.setSize(new Dimension(800,700));
	searchResultsFrame.setResizable(false);
        searchResultsFrame.setLocation(300, 15);
        searchResultsFrame.getContentPane().add(resultTree);
        searchResultsFrame.pack();
        searchResultsFrame.setVisible(true);
        searchResultsFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
  *
  */

         JTree resultTree = new JTree();
         resultTree.setModel(model);
        ForumTree tree = new ForumTree(resultTree);

	JFrame frame = new JFrame("Search Results");
	frame.setSize(new Dimension(800,700));
	frame.setResizable(false);
        frame.setLocation(300, 15);
	frame.getContentPane().add(tree.getForumTreeUI());
        frame.pack();
	frame.setVisible(true);
    }
    
    public void showPromoteFrame(final String m_results) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Vector<String> users = new Vector<String>();
                if (m_results.startsWith("all_users:")){
                    StringTokenizer toki = new StringTokenizer(m_results.substring(10), "$$");
                    while(toki.hasMoreTokens()){
                        users.add(toki.nextToken());
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Error: cannot read users from forum!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JFrame x =  new PromoteFrame(m_pipe,users);
                x.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                x.setVisible(true);
            }
         });
    }

}
