package com.example.onlineshoppingadminapps;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.onlineshoppingadminapps.adapters.ProductViewAdapter;
import com.example.onlineshoppingadminapps.callbacks.OnProductItemClickListener;
import com.example.onlineshoppingadminapps.callbacks.OnQueryItemSelectedCompletedListener;
import com.example.onlineshoppingadminapps.databinding.FragmentViewProductBinding;
import com.example.onlineshoppingadminapps.databinding.ProductItemRowBinding;
import com.example.onlineshoppingadminapps.models.ProductModel;
import com.example.onlineshoppingadminapps.viewmodels.ProductViewModel;

import java.util.List;


public class ViewProductFragment extends Fragment implements OnProductItemClickListener {
    private FragmentViewProductBinding binding;
    private ProductViewModel productViewModel;

    public ViewProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewProductBinding.inflate(inflater, container, false);


        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);

        final ProductViewAdapter adapter = new ProductViewAdapter(this);
        binding.viewProductRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.viewProductRV.setAdapter(adapter);
        productViewModel.getCategories();

        productViewModel.productListLiveData.observe(getViewLifecycleOwner(), new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                Toast.makeText(getActivity(), "Value : "+productModels.size(), Toast.LENGTH_SHORT).show();
                adapter.submitList(productModels);
            }
        });


        productViewModel.categoryListLiveData.observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {

                strings.add(0,"All");
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, strings);
                binding.categorySearchSP.setAdapter(adapter);

            }
        });



        binding.categorySearchSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (i > 0){
                    final String category = adapterView.getItemAtPosition(i).toString();
                    productViewModel.getProductByCategory(category);
                }else {
                    productViewModel.getAllProducts();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return  binding.getRoot();
    }


    @Override
    public void onProductItemClicked(String productId) {
        final Bundle bundle = new Bundle();
        bundle.putString("pid", productId);
        //Navigation.findNavController(getActivity(), R.id.fragmentContainerView).navigate(R.id.action_viewProductFragment_to_productDetailsFragment, bundle);
    }
}