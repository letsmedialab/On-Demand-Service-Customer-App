package com.dhakasetup.sakib.dhakasetupprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dhakasetup.sakib.dhakasetupprototype.adapter.MainAdapter;
import com.dhakasetup.sakib.dhakasetupprototype.model.AdBanner;
import com.dhakasetup.sakib.dhakasetupprototype.model.CategoryGrid;
import com.dhakasetup.sakib.dhakasetupprototype.model.CategoryGridItem;
import com.dhakasetup.sakib.dhakasetupprototype.model.ServiceGroup;
import com.dhakasetup.sakib.dhakasetupprototype.model.ServiceGroupItem;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Category;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Service;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Trend;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        List<Object> data;
        data = new ArrayList<Object>();

        AdBanner adBanner = new AdBanner(new ArrayList<String>(),new ArrayList<String>());
        adBanner.getImageLinks().add(UrlList.adImage+"1503162069.png");
        adBanner.getImageLinks().add(UrlList.adImage+"1505300653.jpg");
        data.add(adBanner);

        CategoryGrid categoryGrid = new CategoryGrid();
        for (Category category: Data.getInstance(getContext()).getData()){
            categoryGrid.getItems().add(new CategoryGridItem(category.getCat_name(),category.getCat_image(),Integer.parseInt(category.getCat_id())));
        }
        /*categoryGrid.getItems().add(new CategoryGridItem("Documentation Assist","http://dhakasetup.com/images/category/Documentation72.jpg",22));
        categoryGrid.getItems().add(new CategoryGridItem("Packers & Movers","http://dhakasetup.com/images/category/Packers55.jpg",21));
        categoryGrid.getItems().add(new CategoryGridItem("Water Purifier & Installation ","http://dhakasetup.com/images/category/Water50.jpg",20));
        categoryGrid.getItems().add(new CategoryGridItem("AC Repair & Installation","http://dhakasetup.com/images/category/AC94.jpg",19));
        categoryGrid.getItems().add(new CategoryGridItem("360 Painting Service","http://dhakasetup.com/images/category/36094.jpg",18));
        categoryGrid.getItems().add(new CategoryGridItem("Interior Decoration","http://dhakasetup.com/images/category/Electrical56.jpg",8));
        categoryGrid.getItems().add(new CategoryGridItem("Building Maintenance ","http://dhakasetup.com/images/category/Electrical88.jpg",7));*/
        data.add(categoryGrid);


        List<Trend> trends = Data.getInstance(getContext()).getTrend();
        List<Trend> trends1 = new ArrayList<>();
        List<Trend> trends2 = new ArrayList<>();
        List<Trend> trends3 = new ArrayList<>();
        for (int i =0; i<trends.size(); i++ ){
            int tid = Integer.parseInt(trends.get(i).getTrend_id());
            if (tid == 1){
                trends1.add(trends.get(i));
            }
            else if (tid == 2){
                trends2.add(trends.get(i));
            }
            else if (tid == 3){
                trends3.add(trends.get(i));
            }
        }
        ServiceGroup serviceGroup;

        serviceGroup = new ServiceGroup();
        for (Trend trend : trends1){
            serviceGroup.setTrend_id(1);
            serviceGroup.setService_group_title(trend.getTrend_name());
            Service srv = Data.getInstance(getContext()).getService(Integer.parseInt(trend.getTrend_srv()));
            serviceGroup.addItem(new ServiceGroupItem(UrlList.serviceGroup+srv.getSrvImage(),srv.getSrvice(),srv.getSrv_sl()));
        }
        data.add(serviceGroup);

        serviceGroup = new ServiceGroup();
        for (Trend trend : trends2){
            serviceGroup.setTrend_id(2);
            serviceGroup.setService_group_title(trend.getTrend_name());
            Service srv = Data.getInstance(getContext()).getService(Integer.parseInt(trend.getTrend_srv()));
            serviceGroup.addItem(new ServiceGroupItem(UrlList.serviceGroup+srv.getSrvImage(),srv.getSrvice(),srv.getSrv_sl()));
        }
        data.add(serviceGroup);

        serviceGroup = new ServiceGroup();
        for (Trend trend : trends3){
            serviceGroup.setTrend_id(3);
            serviceGroup.setService_group_title(trend.getTrend_name());
            Service srv = Data.getInstance(getContext()).getService(Integer.parseInt(trend.getTrend_srv()));
            serviceGroup.addItem(new ServiceGroupItem(UrlList.serviceGroup+srv.getSrvImage(),srv.getSrvice(),srv.getSrv_sl()));
        }
        data.add(serviceGroup);


        /*serviceGroup = new ServiceGroup();
        serviceGroup.setService_group_title("360 Painting Service");
        serviceGroup.addItem(new ServiceGroupItem("http://dhakasetup.com/images/services/AC%20Cleaning.jpg","AC Cleaning"));
        serviceGroup.addItem(new ServiceGroupItem("http://dhakasetup.com/images/services/gas%20charge.jpg","Gas Charging"));
        serviceGroup.addItem(new ServiceGroupItem("http://dhakasetup.com/images/services/uninstallation.jpg","Un-Installation"));
        serviceGroup.addItem(new ServiceGroupItem("http://dhakasetup.com/images/services/Ac-Installation.jpg","AC Installation"));
        serviceGroup.addItem(new ServiceGroupItem("http://dhakasetup.com/images/services/wet%20ac.jpg","Wet Services"));
        data.add(serviceGroup);

        serviceGroup = new ServiceGroup();
        serviceGroup.setService_group_title("Home Shifting");
        serviceGroup.addItem(new ServiceGroupItem("http://dhakasetup.com/images/services/6.jpg","Bungalow/ Villa "));
        serviceGroup.addItem(new ServiceGroupItem("http://dhakasetup.com/images/services/6.jpg","4 BHK"));
        serviceGroup.addItem(new ServiceGroupItem("http://dhakasetup.com/images/services/6.jpg","3 BHK"));
        serviceGroup.addItem(new ServiceGroupItem("http://dhakasetup.com/images/services/6.jpg","2 BHK"));
        serviceGroup.addItem(new ServiceGroupItem("http://dhakasetup.com/images/services/6.jpg","1 BHK"));
        data.add(serviceGroup);

        serviceGroup = new ServiceGroup();
        serviceGroup.setService_group_title("Household Purifier");
        serviceGroup.addItem(new ServiceGroupItem("http://dhakasetup.com/images/services/Lanshan.JPG","Lanshan RO Water Purifier"));
        serviceGroup.addItem(new ServiceGroupItem("http://dhakasetup.com/images/services/PP.jpg","Pure Tech Premium RO Water Purifier"));
        serviceGroup.addItem(new ServiceGroupItem("http://dhakasetup.com/images/services/HC9905_2.jpg","BOX TYPE 5-STAGE UV+UF Purifier"));
        serviceGroup.addItem(new ServiceGroupItem("http://dhakasetup.com/images/services/LSRO-1550.jpg","Lanshan Box Type RO Water Purifier"));
        serviceGroup.addItem(new ServiceGroupItem("http://dhakasetup.com/images/services/LSRO-301-AE.jpg","Lanshan RO Water Purifier"));
        data.add(serviceGroup);*/

        RecyclerView recyclerView = view.findViewById(R.id.recycler_main);
        MainAdapter adapter = new MainAdapter(getActivity(), data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        /*ImageButton searchBtn = view.findViewById(R.id.home_search_icon);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SearchActivity.class);
                getActivity().startActivity(intent);
            }
        });
        TextView textBtn = view.findViewById(R.id.home_search_text);
        textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SearchActivity.class);
                getActivity().startActivity(intent);
            }
        });*/
        RelativeLayout search_layout = view.findViewById(R.id.home_search_layout);
        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SearchActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}
