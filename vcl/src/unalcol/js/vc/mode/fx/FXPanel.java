package unalcol.js.vc.mode.fx;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import com.sun.javafx.application.PlatformImpl;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class FXPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8961808032259120585L;

	protected String unalcol_url;
	protected String url;
	protected Stage stage;  
	protected WebView browser;  
	protected JFXPanel jfxPanel;  
	protected WebEngine webEngine;
	protected String index;
	protected FXManager manager;

	public FXPanel( String unalcol_url, String url ){
		super();
		this.setMinimumSize(new Dimension());
		this.setPreferredSize(new Dimension());
		this.index = url+"fx.html";
		initComponents();
		this.url = url;
		this.unalcol_url = unalcol_url;
	}
	
	protected void initComponents(){  
		jfxPanel = new JFXPanel();  
		setLayout(new BorderLayout());  
		add(jfxPanel, BorderLayout.CENTER);           
		createScene();   
	}     
   
//	public void load(){ webEngine.loadContent(html_code); }
	public void load(){ 
		manager = new FXManager(webEngine, unalcol_url, url);
		// process page loading
		webEngine.getLoadWorker().stateProperty().addListener(
				new ChangeListener<State>() {
		            public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, State oldState, State newState) {
						if( newState == State.SUCCEEDED ){
							manager.ready();
							manager.register();
						}
		            }
		        });
		webEngine.load(index); 
	}
	
	/** 
	 * createScene 
	 * 
	 * Note: Key is that Scene needs to be created and run on "FX user thread" 
	 *       NOT on the AWT-EventQueue Thread 
	 * 
	 */  
	private void createScene() {  
		PlatformImpl.startup(new Runnable() {  
			@Override
			public void run() {   
				stage = new Stage();  
				stage.setTitle("Unalcol Interface");  
				stage.setResizable(true);  
   
				Group root = new Group();  
				Scene scene = new Scene(root,80,20);  
				stage.setScene(scene);  
				browser = new WebView();
				browser.autosize();
				webEngine = browser.getEngine();
				load();
	           
				ObservableList<Node> children = root.getChildren();
				children.add(browser);                     
                 
				jfxPanel.setScene(scene);  
                
				scene.widthProperty().addListener(new ChangeListener<Number>() {
					@Override 
					public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
						browser.setPrefWidth((double)newSceneWidth);
					}
				});
				scene.heightProperty().addListener(new ChangeListener<Number>() {
					@Override 
					public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
						browser.setPrefHeight((double)newSceneHeight);
					}
				});
			}
		});  
	}	
}