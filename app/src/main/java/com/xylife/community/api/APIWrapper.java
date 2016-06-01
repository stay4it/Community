package com.xylife.community.api;


import com.android.framewok.util.Util;
import com.xylife.community.api.util.RetrofitUtil;
import com.xylife.community.bean.Advertisement;
import com.xylife.community.bean.Exercise;
import com.xylife.community.bean.JavaResponse;
import com.xylife.community.bean.Response;
import com.xylife.community.bean.TestResponse;
import com.xylife.community.exception.ApiException;
import com.xylife.community.utils.Constant;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class APIWrapper extends RetrofitUtil{

    private static APIWrapper mAPIWrapper;

    public APIWrapper(){
    }

    public static APIWrapper getInstance(){
        if(mAPIWrapper == null) {
            mAPIWrapper = new APIWrapper();
        }
        return mAPIWrapper;
    }

    public Observable<Response<List<Exercise>>> queryLookUp(String keyword, int page) {
        Observable<Response<List<Exercise>>> observable = getAPIService().getFamousResult(Constant.APIKEY, keyword, page, 10);
        return observable;
    }

    public Observable<TestResponse> queryTestLookUp(String keyword) {
        Observable<TestResponse> observable = getAPIService().getTestResult(keyword);
        return observable;
    }

    public Observable<JavaResponse> login(String name, String password) {
        Observable<JavaResponse> observable = getAPIService().login(name, Util.getMD5Text(password),4);
        return observable;
    }

    public Observable<JavaResponse<List<Advertisement>>> getBannerImages() {
        Observable<JavaResponse<List<Advertisement>>> observable = getAPIService().getBannerImages();
        return observable;
    }

    public  <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class ResponseFunc<T> implements Func1<Response<T>, T> {

        @Override
        public T call(Response<T> response) {
            if (response.total == 0) {
                throw new ApiException(100);
            }
            return response.result;
        }
    }
}
