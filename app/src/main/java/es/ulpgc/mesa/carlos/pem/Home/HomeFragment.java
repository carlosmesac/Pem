package es.ulpgc.mesa.carlos.pem.Home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.ulpgc.mesa.carlos.pem.App.BookItem;
import es.ulpgc.mesa.carlos.pem.R;
import es.ulpgc.mesa.carlos.pem.App.Repository;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeViewModel homeViewModel;
    private HomeAdapter homeAdapter;
    private FloatingActionButton add;
    private HomePresenter presenter;
    private Repository repository;
    ArrayList<BookItem> bookItemArrayList;
    DatabaseReference databaseReference;
    private static String TAG = HomeFragment.class.getSimpleName();



    @SuppressLint({"RestrictedApi", "WrongConstant", "ResourceType"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_books, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);
        recyclerView = root.findViewById(R.id.recyclerId);
        bookItemArrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        fillArray();
        //declaring the recycleView, finding its id and changing its adapter
        return root;

    }

    /**
     * Method that adds all the books to the BookItem ArrayList and set the adapter
     */
    private void fillArray() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("allBooks");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookItemArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String autor = dataSnapshot1.child("autor").getValue(String.class);
                    String image = dataSnapshot1.child("image").getValue(String.class);
                    String isbn = dataSnapshot1.child("isbn").getValue(String.class);
                    String title = dataSnapshot1.child("title").getValue(String.class);
                    String user = dataSnapshot1.child("user").getValue(String.class);
                    String email = dataSnapshot1.child("correo").getValue(String.class);
                    BookItem bookItem = new BookItem(autor, image, isbn, title, user, email);
                    bookItemArrayList.add(bookItem);





                }

                homeAdapter = new HomeAdapter(getContext(), bookItemArrayList);
                recyclerView.setAdapter(homeAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        inflater.inflate(R.menu.mymenu, menu);

        MenuItem searchItem= menu.findItem(R.id.search);
        SearchView searchView= (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (homeAdapter != null) {
                    homeAdapter.getFilter().filter(newText);
                }
                return false;

            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }
}