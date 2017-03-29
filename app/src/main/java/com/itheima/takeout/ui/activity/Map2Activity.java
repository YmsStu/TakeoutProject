package com.itheima.takeout.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.itheima.takeout.R;
import com.itheima.takeout.model.dao.bean.AddressBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

;

public class Map2Activity extends AppCompatActivity implements AMapLocationListener, LocationSource, PoiSearch.OnPoiSearchListener {

    ;
    @InjectView(R.id.map)
    MapView mMapView;

    public AMap aMap;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    private boolean isFirstLoc;

    //private MapAdapter mAdapter;
    private PoiSearch mPoiSearch;
    private double mLatitude;
    private double mLongitude;
    private LatLng latLng;
    private String city;

    //private ArrayList<PoiItem> mPoiItems;
    private PoiSearch.Query mQuery;
    private ArrayList<AddressBean> addressList = new ArrayList<>();
    private String name;
    private AMapLocation mAmapLocation = null;

    private ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        ButterKnife.inject(this);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);


        ibBack = (ImageButton) findViewById(R.id.ib_back);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });






        //初始化地图控制器对象

        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(true);

        init();
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
        initSearch();

        //initData();
        //initRecyclerView();
        //initOnclick();
    }



    /*private void initAmap() {
        // 设置定位监听
        aMap.setLocationSource(this);
// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
// 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

    }*/


    OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    private void init() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(this);


//初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(10000);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(true);

        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {


            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                amapLocation.getCity();//城市信息
                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码
                Toast.makeText(Map2Activity.this, "定位成功", Toast.LENGTH_SHORT).show();


                if (mAmapLocation == null) {
                    mAmapLocation = amapLocation;
                    initSearch();
                }


                if (mListener != null && amapLocation != null) {
                    if (amapLocation != null
                            && amapLocation.getErrorCode() == 0) {
                        mListener.onLocationChanged(amapLocation);// 显示系统小蓝点


                        //添加图钉
                        aMap.addMarker(getMarkerOptions(amapLocation));

                    } else {
                        String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                        Log.e("AmapErr", errText);
                    }
                }
            }


        } else {
            //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
            Log.e("AmapError", "location Error, ErrCode:"
                    + amapLocation.getErrorCode() + ", errInfo:"
                    + amapLocation.getErrorInfo());
        }


    }

    /**
     * 搜索周边
     *//*

    */
    private void initSearch() {
        if (mAmapLocation == null) {
            return;
        }
        String citycode = "北京市";
        String keyWord = "餐饮";
        mQuery = new PoiSearch.Query(keyWord, citycode);
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，
        //POI搜索类型共分为以下20种：汽车服务|汽车销售|
        //汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
        //住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
        //金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        mQuery.setPageSize(30);// 设置每页最多返回多少条poiitem
        //mQuery.setPageNum(currentPage);//设置查询页码
        mQuery.setPageNum(0);
        mQuery.setCityLimit(true);
        mPoiSearch = new PoiSearch(this, mQuery);
        mPoiSearch.setOnPoiSearchListener(this);

        // 设置搜索区域为以lp点为圆心，其周围2000米范围

        mPoiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(mAmapLocation.getLongitude(), mAmapLocation.getLatitude()), 1000));
        //mPoiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(116.365828,40.100519),1000));
        mPoiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        //解析result获取POI信息

        if (i == 1000) {
            ArrayList<PoiItem> items = poiResult.getPois();

            if (items != null && items.size() > 0) {
                PoiItem item = null;
                for (int j = 0, count = items.size(); j < count; j++) {
                    item = items.get(j);
                    AddressBean addressBean = new AddressBean();
                    name = addressBean.name;
                    addressBean.name = item.getTitle();
                    addressBean.receiptAddress = item.getSnippet();
                    addressList.add(addressBean);
                }
/*                mAdapter.setAddressList(addressList);
                mAdapter.notifyDataSetChanged();*/

            }


        }
    }


    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /*private void doSearchQuery() {
        System.out.println("doQuery");
        aMap.setOnMapClickListener(null);// 进行poi搜索时清除掉地图点击事件
        int currentPage = 0;
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query("", "餐饮", "010");
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        LatLonPoint lp = new LatLonPoint(latLng.latitude, latLng.longitude);

        mPoiSearch = new PoiSearch(this, query);

        mPoiSearch.setBound(new PoiSearch.SearchBound(lp, 2000));
        // 设置搜索区域为以lp点为圆心，其周围2000米范围
        mPoiSearch.searchPOIAsyn();// 异步搜索
        mPoiSearch.setOnPoiSearchListener(this);
    }*/


   /* @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        System.out.println(rCode + "rcode");
//        if (rCode == 1000) {
        mPoiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
        System.out.println(mPoiItems.toString() + "----");
        List<SuggestionCity> suggestionCities = poiResult
                .getSearchSuggestionCitys();
        if (mPoiItems != null && mPoiItems.size() > 0) {
            //adapter = new PoiSearch_adapter(this, poiItems);
            MapAdapter adapter = new MapAdapter(this, mPoiItems);
            mRv.setAdapter(adapter);
            mRv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }*/
//        } else {
//            Log.d("Map2Activity", "无结果");
//        }


   /* }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }*/


    //自定义一个图钉，并且设置图标，当我们点击图钉时，显示设置的信息
    private MarkerOptions getMarkerOptions(AMapLocation amapLocation) {
        //设置图钉选项
        MarkerOptions options = new MarkerOptions();

        //图标
        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.order_seller_icon));
        //位置
        options.position(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
        StringBuffer buffer = new StringBuffer();
        buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
        //标题
        options.title(buffer.toString());
        //子标题
        //设置多少帧刷新一次图片资源
        options.period(60);

        return options;

    }

/*private void initData() {
        mLists = new ArrayList<>();
        mAddress = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mLists.add("金百万"+i);
            mAddress.add("昌平区北七家宏福苑小区温都水城");
        }
    }*/


/*
    private void initRecyclerView() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MapAdapter(this, addressList);

        mRv.setAdapter(mAdapter);


    }
*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();

        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }


}
