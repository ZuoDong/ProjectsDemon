package com.administrator.projectsdemon.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.administrator.projectsdemon.R;
import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;

public class WebViewActivity extends AppCompatActivity {

    WebView mWebview;
    WebSettings mWebSettings;
    TextView beginLoading,endLoading,loading,mtitle;
    private static final String INJECTION_TOKEN = "**injection**";
    public static final String TAG = "WebViewActivity ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        mWebview = (WebView) findViewById(R.id.webView1);
        beginLoading = (TextView) findViewById(R.id.text_beginLoading);
        endLoading = (TextView) findViewById(R.id.text_endLoading);
        loading = (TextView) findViewById(R.id.text_Loading);
        mtitle = (TextView) findViewById(R.id.title);

        mWebSettings = mWebview.getSettings();

        mWebview.loadUrl("http://c.gdt.qq.com/gdt_mclick.fcg?viewid=4QVtt41JxuX8a0OXRWVPL1g1ARufMQHrgZ_k6g7F3HS01wjpqJUUu8fI_loMNu2B1gyc3enDjBdqILZzptpydco!8MSUTZgZW2DWiesqTQfow8TxZAnn09_Y2eg0jeLpjcAJcu3NTZ3VNq8b2MC9NGCG!!Yfujf02cmheJPbZ_M2VGYc0UbarhTq6dGnA8BTHVeHoexdHkLPtICRM2Sjg2hoH5!MIuIQ&jtype=0&i=1&os=2?viewid=4QVtt41JxuX8a0OXRWVPL1g1ARufMQHrgZ_k6g7F3HS01wjpqJUUu8fI_loMNu2B1gyc3enDjBdqILZzptpydco!8MSUTZgZW2DWiesqTQfow8TxZAnn09_Y2eg0jeLpjcAJcu3NTZ3VNq8b2MC9NGCG!!Yfujf02cmheJPbZ_M2VGYc0UbarhTq6dGnA8BTHVeHoexdHkLPtICRM2Sjg2hoH5!MIuIQ&acttype=0&s=%7B%22down_x%22%3A-999%2C%22down_y%22%3A-999%7D");


        mWebSettings.setJavaScriptEnabled(true);
        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG,"shouldOverrideUrlLoading: "+url);
                if(url.startsWith("http:") || url.startsWith("https:") ) {
                    return false;
                }else{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();    //表示等待证书响应
            }
        });

        //设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {


            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                System.out.println("标题在这里");
                mtitle.setText(title);
            }


            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                    loading.setText(progress);
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
                    loading.setText(progress);
                }
            }
        });


        //设置WebViewClient类
        mWebview.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                System.out.println("开始加载了");
                beginLoading.setText("开始加载了");

            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i(TAG,"onPageFinished: "+url);
//                SystemClock.sleep(2000);
                endLoading.setText("结束加载了");
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                WebResourceResponse response = super.shouldInterceptRequest(view, url);
                if(url != null && url.contains(INJECTION_TOKEN)) {
                    String assetPath = url.substring(url.indexOf(INJECTION_TOKEN) + INJECTION_TOKEN.length(), url.length());
                    try {
                        response = new WebResourceResponse("application/javascript", "UTF8", getAssets().open(assetPath));
                    } catch (IOException e) {
                        e.printStackTrace(); // Failed to load asset file
                    }
                }
                return response;
            }
        });
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();

            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebview.pauseTimers();
        if(isFinishing()){
            mWebview.loadUrl("about:blank");
            setContentView(new FrameLayout(this));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebview.resumeTimers();
    }
}
