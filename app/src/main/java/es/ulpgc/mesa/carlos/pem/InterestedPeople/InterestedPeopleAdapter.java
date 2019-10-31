package es.ulpgc.mesa.carlos.pem.InterestedPeople;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import es.ulpgc.mesa.carlos.pem.App.Like;
import es.ulpgc.mesa.carlos.pem.R;

public class InterestedPeopleAdapter extends RecyclerView.Adapter<InterestedPeopleAdapter.ViewHolder> {


    private ArrayList<Like> likeArrayList;
    public static String TAG = InterestedPeopleAdapter.class.getSimpleName();
    private Context context;
    Dialog myDialog;
    private DatabaseReference allBooksRef = FirebaseDatabase.getInstance().getReference().child("allBooks");
    private DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference().child("booksUser");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";



    public InterestedPeopleAdapter(Context context,ArrayList<Like> likeArrayList) {
        this.likeArrayList = likeArrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public InterestedPeopleAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.interested_people_item, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final InterestedPeopleAdapter.ViewHolder holder, int position) {
        holder.user.setText(likeArrayList.get(position).getCurrentUser());
        holder.title.setText(likeArrayList.get(position).getTitle());


    }


    @Override
    public int getItemCount() {
        return likeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user = (TextView) itemView.findViewById(R.id.interestedPerson);
            title = (TextView) itemView.findViewById(R.id.interestedBook);

        }

    }


}
