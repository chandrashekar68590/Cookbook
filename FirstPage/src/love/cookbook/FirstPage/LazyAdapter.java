package love.cookbook.FirstPage;

import love.cookbook.FirstPage.IngredienceLazyAdapter.ViewHolder;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

	public class LazyAdapter extends BaseAdapter {
	    
	    private Activity activity;
	    private String[] dishes;
	    private String[] recipeDescripion;
	    private String[] timeToPrepare;
	    private String[] lock;
	    private String[] nonVeg;
	    private int [] images;
	    ViewHolder holder;
	    private static LayoutInflater inflater=null; 
	    
	    public LazyAdapter(Activity a, String[] dishes, String[] recipeDescription,String[] timeToPrepare,int [] images,String [] lock,String [] nonVeg) {
	    	activity = a;
	        this.dishes=dishes;
	        this.images=images;
	        this.recipeDescripion=recipeDescription;
	        this.timeToPrepare=timeToPrepare;
	        this.lock=lock;
	        this.nonVeg=nonVeg;
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        //View vi=convertView;
	        if(convertView==null){
	            //vi = inflater.inflate(R.layout.item, null);
	        	convertView = inflater.inflate(R.layout.item,parent, false);
	        	//image = (ImageView)vi.findViewById(R.id.imageView);
	        	holder = new ViewHolder();
	        	holder.image = (ImageView) convertView.findViewById(R.id.imageView2);
	        	holder.vegNonveggImageView = (ImageView) convertView.findViewById(R.id.imageView3);
	        	
	        	holder.textView1 = (TextView)convertView.findViewById(R.id.textView1);
	        	holder.textView2 = (TextView)convertView.findViewById(R.id.textView2);
	        	holder.textView3 = (TextView)convertView.findViewById(R.id.textView3);
	        	convertView.setTag(holder);
	        	
	        }
	        else
	        	holder = (ViewHolder)convertView.getTag();


	        holder.textView1.setText(dishes[position]);
	        holder.image.setImageResource(images[position]);
	        holder.textView2.setText(recipeDescripion[position]);
	        holder.textView3.setText(timeToPrepare[position]); 
	        /*
	        ImageView lockImageView = (ImageView)vi.findViewById(R.id.lockImageView);
	        if(lock[position].equals("1"))
	        	lockImageView.setImageResource(R.drawable.lock);
	        else 
	        	lockImageView.setImageResource(R.drawable.ic_action_search);
	        */
	        
	        if(nonVeg[position].equals("1")){
	        	//System.out.println("Inside non veg");
	        	holder.vegNonveggImageView.setImageResource(R.drawable.non_veg_symbol);
	        }
	        else{
	        	//System.out.println("Inside veg");
	        	holder.vegNonveggImageView.setImageResource(R.drawable.veg_symbol);
	        }
	        
	        
	        return convertView;
	    }

	    public int getCount() {
	    	
	        return dishes.length;
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }
	    
		public class ViewHolder{
			ImageView image;
			TextView textView1;
			TextView textView2;
			TextView textView3;
			ImageView vegNonveggImageView;
		}


}
