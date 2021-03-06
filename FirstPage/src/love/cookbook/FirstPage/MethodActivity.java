package love.cookbook.FirstPage;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.*;
import android.media.MediaPlayer;
import android.os.*;
import android.util.SparseBooleanArray;
import android.view.*;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.*;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

@SuppressLint({ "ResourceAsColor", "ResourceAsColor" })
public class MethodActivity extends SherlockActivity {
	public MethodLazyAdapter adapter;
	View vi;
	public int noOfSteps;
    private static LayoutInflater inflater=null; 
    public Boolean tick [];
    
    public CountDownTimer myTimer[] = new CountDownTimer[30];
    public Toast t[];
     public final int count =0;
    
    MediaPlayer player;
    String vibratorService;
    Vibrator vibrator;
    
    public SparseBooleanArray checked = new SparseBooleanArray();

	 @SuppressLint("ResourceAsColor")
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        setContentView(R.layout.method_list_main);
	        
	        //To enable back arrow mark on action bar.
	        com.actionbarsherlock.app.ActionBar ab = getSupportActionBar();
	        ab.setDisplayHomeAsUpEnabled(true);
	        
	        final Intent intent = getIntent();
	        ARRAY.preparationSteps = intent.getStringArrayExtra("STEPS");
	        ARRAY.timeToPrepare = intent.getStringArrayExtra("TIMETOPREPARE"); 
	        ARRAY.arrowValue = intent.getStringArrayExtra("ARROW");
	        noOfSteps = intent.getIntExtra("NUMBEROFSTEPS", 0);
	        	        
	        tick = new Boolean[ARRAY.preparationSteps.length];
	        myTimer = new CountDownTimer[ARRAY.preparationSteps.length];
	        t = new Toast[ARRAY.preparationSteps.length];
	        
	        for(int i =0;i<tick.length;i++)
	        	tick[i] = false;
	        
	        final ListView list = (ListView)findViewById(R.id.listView1);
	        
	        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
	       
	        adapter=new MethodLazyAdapter(this,ARRAY.preparationSteps,ARRAY.timeToPrepare,ARRAY.arrowValue,noOfSteps,list);
	        list.setAdapter(adapter);
	        
	        
	        list.setOnItemClickListener(new OnItemClickListener() {

	        	
				@Override
				public void onItemClick(AdapterView<?> parent, View view, final int position,
	    				long id) {
					// TODO Auto-generated method stub
				     RelativeLayout ll = (RelativeLayout) view;
					  ImageView image1 = (ImageView)ll.findViewById(R.id.imageView2);
					  final TextView textView2 = (TextView)ll.findViewById(R.id.textView2);
					 					  
					  final int count = position+1;
					  
					  boolean stat = checked.get(position, false);
		                checked.put(position, !stat);
		                adapter.setChecked(checked);
		                
	                	inflater = getLayoutInflater();
	                	View layout = inflater.inflate(R.layout.toast_custom_layout, (ViewGroup) findViewById(R.id.toast_layout_root));
	                	final TextView textView = (TextView)layout.findViewById(R.id.textView1);
	                	textView.setTextColor(Color.BLACK);
	                	textView.setTypeface(Typeface.DEFAULT_BOLD);
	                	
	                	t[position] = new Toast(getApplicationContext());
	                	t[position].setGravity(Gravity.BOTTOM, 0, 0);
	                	t[position].setView(layout);
		                
		                if(!ARRAY.timeToPrepare[position].equals("NA")  && checked.get(position,false)){
			            	//playAudio(); 
		                	
		                	list.setEnabled(false);
		                		                		                	
		                	textView.setText("Step "+count+":Timer Started");
            				t[position].show();
            				
			            	myTimer[position] = new CountDownTimer(Integer.parseInt(ARRAY.timeToPrepare[position])*60*1000, 500) { //the timer runs for 30 seconds in this case
			            		
			   	            
			            	     public void onTick(long leftTimeInMilliseconds) {
			            	    	 
			            	    	 long seconds = leftTimeInMilliseconds / 1000;
			            	    	// System.out.println("tick of position: "+position);
		            					textView.setText("Step "+count+":"+seconds / 60 + " mins and " + seconds % 60+" seconds remaining");
			            	    		t[position].show();
		            					
			            	    	// textView2.setText(seconds/60+":"+seconds%60);
			            	    	 //alert.setMessage(seconds / 60 + "mins and " + seconds % 60+" seconds remaining");
		            	    	 	// progressDialog.setMessage(seconds / 60 + "mins and " + seconds % 60+" seconds remaining");  

			            	     }

			            	     public void onFinish() {
			            	    	// progressDialog.dismiss();
			            	    	 list.setEnabled(true);
			            	    	 t[position].cancel();
			            	    	 alert.setTitle("Done!!");
			            	    	 alert.setMessage(ARRAY.arrowValue[position]);
			            	    	// alert.setIcon(R.drawable.savai_upma);
			            	    	 //playAudio();
			            	    	 vibratorService = Context.VIBRATOR_SERVICE;
			            	    	 vibrator = (Vibrator)getApplicationContext().getSystemService(vibratorService);
			            	    	 vibrator.vibrate(1000); //vibrate for 1sec.
			            	    	 
			            	    	 alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			                             public void onClick(DialogInterface dialog, int which) {
			                                // Some code
			                             }

			                          });
			            	    	 alert.show();
			            	     }
			            	  }.start();
			            	  
			            }
		                else if(!ARRAY.timeToPrepare[position].equals("NA") && !checked.get(position,false)){
		                	myTimer[position].cancel();
		                	textView.setText("Step "+count+": Timer Stopped!!");
		                	t[position].show();
            			}
		                	
				}
/*
				private void playAudio() {
					// TODO Auto-generated method stub
					player = MediaPlayer.create(this, R.raw.facebook_ringtone_pop);
							
	            	player.setOnCompletionListener(new OnCompletionListener() {
						
						@Override
						public void onCompletion(MediaPlayer mp) {
							// TODO Auto-generated method stub
							player.release();
						}
					});
	            	player.start();
				}*/
			});

	      
	 }

	 @Override   
	 public void onBackPressed(){
		 super.onBackPressed(); 
		 
		 for(int i=0;i<myTimer.length;i++)
		 if(myTimer[i]!=null){
			   myTimer[i].cancel();
			   t[i].cancel();
		 }
	 }
	   @Override
	   public boolean onOptionsItemSelected(MenuItem item) {
		   switch (item.getItemId()) {
		   
		   case android.R.id.home:
				 for(int i=0;i<myTimer.length;i++)
					 if(myTimer[i]!=null){
						   myTimer[i].cancel();
						   t[i].cancel();
					 }
			   finish();
		   default:
	           return super.onOptionsItemSelected(item);
		   }
		   
	   }
	 protected void onDestroy(){
	    	super.onDestroy();
	    	//unbindDrawables(findViewById(R.id.RootView));
	        System.gc();
	 }
	 
	 protected void onPause() {
		super.onPause();
		//System.out.println("Method On Pause called");
		/*
		 for(int i=0;i<myTimer.length;i++)
		 if(myTimer[i]!=null){
			   myTimer[i].cancel();
			   t[i].cancel();
		 }
		*/
		
	}
	    
	    private void unbindDrawables(View view) {
	        if (view.getBackground() != null) {
	        view.getBackground().setCallback(null);
	        }
	        if (view instanceof ViewGroup) {
	            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
	            unbindDrawables(((ViewGroup) view).getChildAt(i));
	            }
	        ((ViewGroup) view).removeAllViewsInLayout();
	        }
	    }

}
