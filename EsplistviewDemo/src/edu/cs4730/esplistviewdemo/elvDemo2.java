package edu.cs4730.esplistviewdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;


/*
 * uses a simpleExpandableListAdapter without extending it.  
 * lists are created from the group and child list in method below.
 */

public class elvDemo2 extends Activity {
    SimpleExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    ArrayList<Map<String,String>> listDataGroup;
    
    ArrayList<ArrayList<Map<String, String>>> listDataChild;
    //HashMap<String, List<String>>
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_elvdemo2);
		
	    // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp2);
        prepareListData();
        
        listAdapter =  new SimpleExpandableListAdapter(
                        this,						    //context
                        listDataGroup,                  // group list in the form:  List<? extends Map<String, ?>>
                        R.layout.evl2_group_row,        // Group item layout XML.
                        new String[] { "Group Item" },  // the key of group item.   String[] groupFrom, 
                        new int[] { R.id.row_name },    // ID of each group item.-Data under the key goes into this TextView.  int[] groupTo
                        listDataChild,              // childData describes second-level entries. in the form List<? extends List<? extends Map<String, ?>>>
                        R.layout.evl2_child_row,             // Layout for sub-level entries(second level).
                        new String[] {"Sub Item A", "Sub Item B"},      // Keys in childData maps to display.
                        new int[] { R.id.grp_childA, R.id.grp_childB}     // Data under the keys above go into these TextViews.
                    );
       expListView.setAdapter( listAdapter );       // setting the adapter in the list.
     
        
	}
	
    /*
     * Preparing the list data
     */
    private void prepareListData() {
    	//create Group List
    	listDataGroup = new ArrayList<Map<String,String>>();
        for( int i = 0 ; i < 6 ; ++i ) { // 6 groups........
            HashMap<String,String> m = new HashMap<String,String>();
            m.put( "Group Item","Group Item " + i ); // the key and it's value.
            listDataGroup.add( m );
          }
    	
    	//create the child list, which is more complex
        listDataChild = new ArrayList<ArrayList<Map<String, String>>>();
        for( int i = 0 ; i < 6 ; ++i ) { 
          /* each group need each HashMap-Here for each group we have 3 subgroups */
          ArrayList<Map<String, String>> secList = new ArrayList<Map<String, String>>();
          for( int n = 0 ; n < 3 ; n++ ) {
            HashMap<String,String> child = new HashMap<String,String>();
            child.put( "Sub Item A", "Sub ItemA " + n );
            child.put( "Sub Item B", "Sub ItemB " + n );
            secList.add( child );
          }
         listDataChild.add( secList );
        }
    }

	
}
