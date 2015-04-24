package com.android.decipherstranger.activity.ShakeActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.GameActivity.WelcomeActivity;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.util.ShakeListener;

/**
 *                 ┏┓　　　┏┓+ + 
 * 　　　　　 　┏┛┻━━━┛┻┓ + + 
 * 　　　　 　　┃　　　　　　　┃ 　 
 *　　　　　　　┃　　　━　　　┃ ++ + + +      
 *　　　　　　 ████━████ ┃+            
 *　　　　　　　┃　　　　　　　┃ +             
 *　　　　　　　┃　　　┻　　　┃               
 *　　　　　　　┃　　　　　　　┃ + + 
 *　　　　　　　┗━┓　　　┏━┛ 
 *　　　　　　　　　┃　　　┃　　　　　　　　　　　 
 *　　　　　　　　　┃　　　┃ + + + + 
 *　　　　　　　　　┃　　　┃　　　　　　　神兽保佑　　　　 
 *　　　　　　　　　┃　　　┃ + 　　　　　　 
 *　　　　　　　　　┃　　　┃                    代码无BUG　
 *　　　　　　　　　┃　　　┃　　+　　　　　　　　　 
 *　　　　　　　　　┃　 　　┗━━━┓ + + 
 *　　　　　　　　　┃ 　　　　　　　┣┓ 
 *　　　　　　　　　┃ 　　　　　　　┏┛ 
 *　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + + 
 *　　　　　　　　　　┃┫┫　┃┫┫ 
 *　　　　　　　　　　┗┻┛　┗┻┛+ + + + 
 *          @Created by peng on 2015/3/23.
 */
public class ShakeActivity extends Activity{
/*
    private final String str = "iVBORw0KGgoAAAANSUhEUgAAAVQAAAFUCAIAAAD08FPiAAAAA3NCSVQFBgUzC42AAAAgAElEQVR4\n" +
            "nNS9z4slR5Yu+E2ORXPscRPcIQTXITWkQyboJiWoSFrQGaABBdSi1GjRVfRiumGg6WUv59+Y/cBA\n" +
            "P5iB7rcYqmfxKPWiIGshiBxGPREFKvIKKuEmKBm/oAA3yMuzQ4dNMgv74Wbu5n79RkRKegeRuuFu\n" +
            "bm5mbufYOd85duy/ufiP/xNA6IjZAAIk4ouOhpfYcO8OCXDvEWFLppUIAIAB0JUPhQE5fHv3VNIA\n" +
            "DYBEVN5oABAjNQwo00/XJA3fZgjbJD2nSYDtl4TIjZi7G1UlZKYNw2rNZGNBELYMDwqQH+29JX2N\n" +
            "Jn+dsp1NHyEBCPLv6toHM/X4BMWN2VODGe8SgLhTcT0m91kBCGlv9efzTWlsVGeR5xcaVmXSTgfO\n" +
            "FWmXO24FABIQ3cywfGvIji9lmCczRpGMkN2HiUfTsQ1IaJh40KW9G49mxPxzybVTIPkuY6OckWj5\n" +
            "2UK+2RKkfRmJUqfl5UGToRsf29pQLfULHESGgKhrEzPMlhH+7ebgmdzNicHEkkDcqXio54rhHGmA\n" +
            "ohdNTEopugJ6NpuNSPTkhXQDpvWPJIIjv05oHsiUKRHmq7L870TtgFwN6WeKGhBYzfQXZ/gVtf/K\n" +
            "bENcjTov2HqrXFdGJ620v11bcyth8q7oEV8t9UYsu3KawaiKDANrsKssYVff/0gd0OOfyVarEa2x\n" +
            "3dKdPKXBVgRLA/hxOZhhhl0LQxw1WGLPmh9VmLmmhW+Yv9vxHly1/qWRlhF4cv9bs2/vWquHc9C+\n" +
            "SEAbQsfMPCUEIxprUqKNxhdNtERNKxrD9+zRp8I31zRe2Km0Rs9VoyIVG+kyM6hAgEb0k0nO/0Ho\n" +
            "fbzLQGeqJccnbuI6NnasPlj09lAoHzgnGkudY9ofnULfYxryyQHM/P5o1Ox6D+S/Y/fqm82EWYUn\n" +
            "lZKhnbv31cNrifzwEmhMqMwUNn2pmbX5kYxFhxyI0bbmm5S/kOOo/CKfJymgTbJ6SEFOvzIHNNKt\n" +
            "tCKIj+h6qDZqIW6iJI/pSZypaubA5uZoJBHIXwnfrRsr2TPBojce0LW+iTRB/bvSD/hMys4Byq39\n" +
            "3cVggc8dTwk7KTPlxxAH99SwTND5fcnMuFJ4ZFw9EYk0coLtxgJd+hdnOpl8DdH/0f9WozrPyPVO\n" +
            "s3XTceY8yy5xvio/p8fM6QQOHLkVF8iWN9BgJ5Vutl6JeJrmpd5o20b7Pv1KnvwTAHddfk+L8ARN\n" +
            "LLD+lp/i02qXHc9gsqU0v18Rqj0UFhxzTXSFOiu4x486RioCNpe10wEPq6XIur+DaGreSq++Dawz\n" +
            "jw5pXnYk5jbRAGEFI/+Zx8Zndqu6t992qH+S9JPq0aEK+Xuln8zI9JZYQUKy0d38hmtrDu0PlOWs\n" +
            "7qLDRTxgjqxDbox6i+QISa9/ao/3JDUYTiRpd+twT00W9/KGuhTUtwOR+xPjd3PGyE1pHnywdy5m\n" +
            "e3QzukEld8W0Q8zC23FSeN+EsebY2LjFM2e8/n1ahqU939hi/oFljO63aXxYRtf8+MGszT/dpEzx\n" +
            "GeiCV/s7VqcDLfm9Jb0vh9EJgqnH5/t+Rt8eunDj2TkGFP2Qy9Scd/1EVqqJZtxiuMKyEV4hB9ND\n" +
            "ikMmTGrEHUoeF0uh/siTxyn3knOcz1a0x7+4cCu2SJd9wJsWrkHu/31TVo7LAhmcf5E8G2nCTEpt\n" +
            "Zjm8Pl5tRkDcgqSgCScfgLsLDJlBB8HdE7brT4Tnh3RHknF8DpDsfU3TtxDnwmA3Ees2/iVZ8Mmy\n" +
            "t9EhqscSAzxsiw+W8fFjjhIEKPeVxeS3n+yymC4wRCnG6AAZ1i86NbhZ5sxePIQ5A6b9vll6Pt2e\n" +
            "N/YaKT+u/XyTBuy1gKICJn3F3fZ3KA565CxuD+BNVTWx1qb1R54138+M86LnljmEfnjUyngRElb+\n" +
            "H3FSzsMmfqJtGDOU3ERkNy9mOrTv1tMufBucKtrp53f2imxVe234mZQd26k6JUQvkBxsIl9dd32G\n" +
            "/jF4EQ200PDnoKUH8fO+Yeo7NuYbKrcjG1d3t6p+Qj8RDfk9zX5LkRfWOajnmxI/AEWgT279mtgE\n" +
            "kZAOGv577cVB2kSMvofHAYz3qqfwh+tdoI3JuIFF9ucozetDhPb7K9b4z2+MOVgizHxAv1f+/1Go\n" +
            "t9Qb1oCk3JS4wVTuFp+fgEUzbEOyhewQrX6cLKo3nCe68/U6Ojg8eUijUzHa8+LDImyUbQKTT3zQ\n" +
            "7C3r2BegLmSgP6QigQQmBcyeFozSvq0Tc2JO0rsTGOOsevpvH2wKmOXrSgdpODLzweqBn3UP9Vr4\n" +
            "npYsX+1PMfz4bqnH+f7i3QcvTyv/wFD/vzn5SRI2v1jitMgN6h1ZemPQottsh3Sjjt+3IrLRS7cj\n" +
            "j2BE7ctGvyUA6p4NVT0oaOJuSpSLqRqo1pTKvihatle+72dxL9kPfNzOEXXHdCtHaTxWueUpGSvK\n" +
            "Sca+jSAFa5NGWLuYztFWSEF3vR1DRq/zv43uO/My3clvMO/2/Irk+rBf/V7eSuGbg0YCqdoubyvh\n" +
            "+sRI1DaKbZ+oDCUP9OgumjS32oyikQLR9tbN2CYrO+4K2bozGuOlwWS8nVPTI0Hx16fDDcMfVRNK\n" +
            "vp2PC3YXB3FBPRfgKL7Xufen373P3N5vwPsCwb8yX8/xHehvHXHU77tOvnTO8S4A0J5AYMNRwyYX\n" +
            "nITmTdxoPwx5mDdD89f2LMW+X9HXA/O1vWdT4kchv49jsLnLcDSjyKoD8DZ/L9rnsGixHokUO8jE\n" +
            "XPg8NKPKde/veKdYbyO8xvSOYwGx386/W5pYdvb6Duca4dkAmzwqpscevHOkcFrGzfgKd7Li/CQN\n" +
            "+Pcz9fozbegXGNU4ZGR/zYn2G6oPY490K81t5WCSDoMSKTBPgptgO85844EZst4bDdk17sGhuhwP\n" +
            "fnjqc+xwuoxupx1/XfqIcJvG/ANZ7P5wik2J2GQwuYv99hGLXPvf37o9FvryHinNpzIwB+K7Mf+H\n" +
            "qRWz91x1YG+xwFlG34DLGPCJQCR5gzpEBA7zdNwiyGeM5linN4U0U4Wfo08SfbkxiCtznX3yqf00\n" +
            "sl32JjTprRoiFLegmNURuRXGfEg/vGf0oFCxgyRCv/CEYjgtqd3+ET/ZfC4D8WMkYuk+WW8jcJcp\n" +
            "yIoADLLyDNsq3JqTSzV5QzIjv++CtPfM5RQt3zszAgr0jbdQTxiWJMq6E70CMGB3JfJWoAuGszvG\n" +
            "YtMxEzNjW+i3IVIHySaCpR8lsZc57zbUBz6H2RyenO+enFmVfb+jO12VMmvSXhsrs/7PZfjhyBz0\n" +
            "mcaGdLSSgUM9K1VTcT/ju92hki8GP0Zo5raqvpPGUza8x3N7tAvY+JKxmDQu6IKizZIEySaaQLHj\n" +
            "xDh+lohuhSnikv8kSYEYzpdjX6qdOCMcAQCu93d8Fo19+x+A5vsmDsIXZ7b/oACN/XQrne+m0UE3\n" +
            "YDqXgCva+SOHjgBvDmi2kN/encY/ASN/P0mx/yNJQRCulCQCuN1puZBusRMyEU82b5dhGLAgl+pP\n" +
            "lK6AYRgCkRTQOwagBcGAmSFsNBWTIOeKd7sAySkaBjoYivZF4SNcc6e8xUhVqnP+VOjgBWoO/Th9\n" +
            "TBeMW7chBw38MLSPDTpB1PP6+0Tlfl66MfivhP/3fjOXMCt8GICE7LZMm+66M72dCIBmMINZgwCj\n" +
            "mbndMQTKRdFst23TKGYAzIDRRJIWRERSlrSgOMyZCHJBy0XpcT0GbOwKOy80osWwQ2P5sBUShyzy\n" +
            "h5QkEA/TWt0sBqGjgY592wp/EiEMU5GtN/anzrb75gDRqc0/K0RnHt1OwxwGV2NEiMY2WFp+xLdn\n" +
            "2BoLmhkAiFpbgVuBwXb1Bm0UlFK8081b1WybZttsmy0JANRyy8wEkgvZqlYpFa3MTFTYn2QlALnr\n" +
            "1pKvPlguH1TVh1VZFMvjShIK6poXOai8KWFBATFguUC3VuZpMFY8snjwmMV7e3aN6TZVHR4TIXtx\n" +
            "lgh+/v6wTHiRDlvhD4I591IfWZD5TTSTz3X0o+zGm7TzZ27nmK4/zfnlP0Aw5pVS7VXbfK+UalsD\n" +
            "daWaq1Z9r9qdbnZbvgIVjhu1jU0SJIXShnkHCB+lA9COOX6RZwwSFozcFAXRgsqirD5Y1g+ruqqK\n" +
            "gspFSQJMRLYqF2HCs2J4f1y6pT5y+NkhN3/XROHbjfBw9u7hv/f7QUcZNvZ5CGAf1P9DcT5G+X//\n" +
            "5EhyM02QABitau1vVtzuWO3QMqu23bxpNt9tm23LzC0DAsyh2gIFsb1gW0gEQBkGiApi4xUJQMEt\n" +
            "powI23ehdawMWgVcKTaqWDTVN+uSqChk9UFRFGV1XFQflMWCIEgKEIGI4NSHH59IUKcUTEzfg3ip\n" +
            "X/j9oP0TNPACTMaJjG2rTWevN5xdmGDmLe+H9o98B0E6+ItBsW/JGWEeKhw8eUsaZq8HcopTnKhv\n" +
            "rAoH2kcnxiRkyPUS4B03O62uVPN9A0Hlfblpms3rTdMwG93uWO1Y79hupWTL3IlCyB7Jj/12/re7\n" +
            "ziBypzt1CD9H9cQQALNBc8UKjK1av2qIUC6KopDlfSIiCBQLqo6rqiqr5XJ5XMKKuQ6RYeZIv+hG\n" +
            "pre7aQ+NKfnZst3cF/lt5FNL7m0ZoOeiH4qJsdikYQzIWFUHtWGcks+RXjf5kn2aPVY+O256Mfrd\n" +
            "C/sNt3JvvpXYviHNc+zBfqc0JhcAYKKTpFyFViAwUPCO12/a9Z/Wbm1XmgpJRNurttmq6LhBsvAe\n" +
            "Z6dDLn6B/G+CRQ0ioeAqYQDUs/GMlbYEgAEW5MrsuNkxtiytpmAYQLFYVx9WZ39+enJSlQsGEQSD\n" +
            "2faNRHDtzGL2/TsiDiHX631VTa/gg+0oNIxFy1aVf12YkLOmpY1Y9bXeaCaPTd2+Gjvbz31bGqKJ\n" +
            "PdW+8/MP4mGirJszKN/z/Tvu87fHHST+Vi8UlyRIC+4WZDhFAJAQxAbNtrlcby7+sF7/aa2sPm+A\n" +
            "HRER75gNZ88jzu9+MZ7D45JRkolwomNQnRyWZhyy0A8fTZz/HESGFqx9vEB7xc1WQaNVulqi/rCu\n" +
            "lqVtswSzZZVB+9+TqXYzjfxQ+TJRvrsl3htY8F8bDY76sxejVPpmWBgAhJu1ol/oVnRT2TYhRPPX\n" +
            "k0A9Xwk0IBnEO968bp7//vxivVE7xQYQRAS2rvsdM/InkY+tAGzGj38UznryNTAQ7an2a5HlVbhJ\n" +
            "7I4Dyq/GDkQkWjAMLr5db940VYG6rk9+tlo9rqplBUHSIOnFEXAdgIa75o0oam1/EHL+7SN4wdx4\n" +
            "JMqUMYMCPphiBg32zP+UgdVRyor6/FFfMXVn9d0N5x/O9hOrfa+AK5b8xWANAYgSHcAuGbR+3Z5f\n" +
            "Xl5+s9682sBA27norFvqQI29Db4lI/U75XDBbnZapDAoBcKmXiIQFCs2IEEkAMPNlWoVNtvLl5vm\n" +
            "yeNq9bOnJ4+X9bICAwsfoch+Bo/06w6D0ffz/0yaP2dywQU+h3oEcwxHYIDDpRSNikhL3o4Oy//f\n" +
            "o9ku/SztOckjfsOdrflucA/QOuPR6QmCCdC1O+hy4X5qm07bAMyXr9a/fXFx+fVlcwUCZOE430Xs\n" +
            "2aUycH5ux0Poi9f/43B3okGx/rP+SE9n89vIorjC+PHoesj/Q0BBhVNSDAAiAsCasXm13b7evvxm\n" +
            "3Xy8Wv3s6dPHS4gS5CFGQUR0eybP6CODz5GJgJ9JN9INE6A4pHIOO23G6jfZLzVED3r9mDuEM7eQ\n" +
            "HiwIDiqc6Cx9to/OzkpuZRre0wX2GP9ZTcnoka+7RyhkBUFSINMAgrFRGaQBddVefLP+8uv1+auG\n" +
            "d4QFFENZBkQH/BIsUD6i9qPD7RJMuX+i7r7og2Dzd48EjNApqL2HPX7ObFBQQUTMzMYt7CxsEBHY\n" +
            "cPuGlbo8XzebT548/fhk9agi6x1MoxjukJLexo6Y/Biq6CFP+YV3LtoeHa1ByJ5B3HvLGP+MNONm\n" +
            "NK2xWp5/74lkXRv0mHnf7VKJbf7brvkzxzc/3BoGkiRy5yVlyGFgGkKG9+rI5caMi/X6y989v3zD\n" +
            "zgMvwlN+moqu/3k+MZ0z33r7knU+6WAUddfDcqdHVYz87rRTAqCYYQKkT157AQAiYoOWuX2z/VJt\n" +
            "mTWJp/WDSi6ILaJJaZ37rRt0C6C36odEOUaNplBUQ+dtCT2lKJw5HgFvCpn48Xg1zuKMOYkR6boa\n" +
            "slvs/J6urMsmqL93avBrbWBXO7/mH+CUuRkNeNkNwBiPd5NiuMLPBfz30DDG+YY7nTSirxvLdUEA\n" +
            "KebN6+b5i8v1K9WCIAp4C3uAzwNhGR/i+YlhGQCk2XRX0ElidMB1xXsi2askBDSKz79eM/Ppz56u\n" +
            "VnVxvJSCYdgvyB2HZBboI+D6YCCAOhNg1EkWvcvmk5IwLraq2yBgC3IPootFqv/t/CwRztAPNM4c\n" +
            "YhWpe7HVNmzwQX7+PuVVVJP/nUW48vJAANmEX+Ory8gBfnZvn46KORZ4b+pIZ2bAnUkyaNPB7/cf\n" +
            "KLXuSApqDdavNs9fXFx+u2nhJxPg16rUAz+98ApCz/+X9dbehsaw7tgfK4gBMtHsN9EyHqVnI4HN\n" +
            "VjGv25Yb1a4+XtUP6pK85BMduMi5UM4ooIviK7Mp8BX5VZ3BAAXtzGdqNhYMIlc4jHYgomgrdMzz\n" +
            "QAipjkrbYv353pUpdNI21oa7Q9bmKGg9OswXMOTDn8rWuKAI3Cnzd5XN6vmNDCEp/WezoT7MDKLm\n" +
            "Sp1fXlx8s252gJ994ZkQdZ/R+nLoroMDB9czFFey133lH4m12Jy5bBcidiGGtjv5VYv8h2S1w/pV\n" +
            "o3bc7hifSHpIsmtVtC8ou/HZdHKhG6W5XyfnfgNwRDDcbQ8VBCIGAdzGi54BQC3bzkLtmHctAFqU\n" +
            "zLrdKdi8Jna7tK1QgIJB5+NnSRAtZCzCEq1eQNqEK9M8v5e908eHub26fuXgvR/9CJlMGq87JKfQ\n" +
            "G3/yb5erN1zpCh7qBYkC+Lvpy6DNq83zy/X55Xp9pWxgDyUpd4NTrVdfxA8z+XYMiehX6lqZocQ8\n" +
            "pqCcpA1grxUTCYtKhpoZ/cJgAxBpQWBuvt00Sm93+JtP6/rRqlyQzS9AkniQ2blHN1Z8u4C86AO1\n" +
            "mgCypwlpw62xAplYceu3TDCsC7NV3ze8Yw2oK9WqBkBZVGx0+72CYUgiQWyYr51MpyPr/KCQS6s6\n" +
            "LqvjoihLWRR1UVFBhQBQsGFmZV2nbtmfTsUzIfSDqiJmoQM9/v/ROX9Id9yiRLk3gNDT+5pj2uvw\n" +
            "94zqY/u8Knj+9eWXvztv3Y56C41lImfmeqTE+J99iGhy/Oavn6k+wiM1RxZBJMI9osk+rKjZbs9f\n" +
            "XKyOWS5IPqy7pw1nG0NHwBE4Sh9EMvlzX+O59Qq5pD7y3yjVXrVbtdVvNRusX23aq616q7sTqQ20\n" +
            "Dbjyn895eWkNk3p8jd+MEg1O2O9od0CVi6L4oKwfVNVyWX1Q1g+qoiiICitDtWE5JeWHKkyWye8W\n" +
            "F7wpHWaDjNZxd5SxjaX7V9wR1GmVWDgjUKn24k/by8uLzZapAAsCAVTAhA323HvK0551zi41/Suh\n" +
            "tsDYoi8R+nG7Q4jBnbie004dEglvGHMiQaKStoyPFyLrGmQBIoJBs+PzF+e808+ecf2glgvia56O\n" +
            "U6Kj6fEYJ0Gld1jSEfgamplZN0zbbftyvV6/2my+2/jFHq1yYVbsI65CL0gAorBiu7XjRiAhtUd5\n" +
            "GTJBMe1p9gIEtMzY8eZK4bUq1w0ETpa0elzXj+r6w6paVrbabhYGe+RQleeQxXw6IYXsfdZDafrt\n" +
            "M6pNKhhzEkY0G6LvTfpRtp/c4SfSew60s5okeMfN1fbL3/9281ph4XtL5LRmqyqL9DNnI/mH63kI\n" +
            "BYyIDRM5iWCXqdDH0WCBsRf1agaTBcDE4Ep4MNY2ezaC8XpOZ2PT+o/MuxdUyPK4kgvSzNlNwbNX\n" +
            "eH+IlbE4f1IXG2hmtdPMqrlq22a7vWp+89WGd9zunGPC43bEYCKS5B0xViOPKmTDcQiGDXOAxaiF\n" +
            "j5myTgIvCEKv7b7pRjEMk8Lmzbb8+rKuq2d/fvLk47ooytT8lDI4wIVEYlIlMuEG21onjv2YemrO\n" +
            "i4Ya5U3Fh0jdAxNvH2f7oZjISbj5cftjeRFKo8HMVGkDBdoo/s3vN+ffskIB7/fyZjRFa3KwMJMo\n" +
            "vdHu5LrgamC/90aQh9xHlo1II+C4GeHxUGdY+fw64HYQiL4KHTzwXT32qi/F0b9NQc0V89cbVM3Z\n" +
            "oigW5M+l6WpjpxBlzYHEQZgG9nR3NEoAm51+8eLy4tu1atvmSjVXLe98rKFwUU7RMBHvmHfeE5F+\n" +
            "I1fCXjdJN73GhM5+CWGXfrSdmCACqAHA2DBeqva8WZ98y/XD+snjarksKgGASTCgXWYnk2wKJxcI\n" +
            "RgzWYATVZjabRXmcOb0+mHgiGoGo/n2xeHdAIl57vSC4dTKvROe/sxyGzFwsSjZsQf6Lb15efvNS\n" +
            "sQeBhuXDP2EGi6FK4ojEaIzz6PYHAZq1sWzKSuw8Cz4kIfE1zKeeDmnABptts/5mXRV08qgiv26H\n" +
            "FndHd2VF2JHLI8yGSVqh6sOfBHBUaM2bbbPdNuffrC8u15vXjfJwjLU+Qqu62gNm5m/RcFTHySlc\n" +
            "dtgj4Ru2TnZvNGydAnYcmtdNe9WW36w3q+XTnz/F46okgDQE5KJ0e8TRe5wIfBv/v6eQGuT2Vd2W\n" +
            "eup8dpaNbRIaC9rN0kz9KF+sy38SkyBm3RoJgZevNs+/etFst34mEVvfWKwq++BTigX6EEibcSVq\n" +
            "w+Sfw1thRvaMiyGv2lePyKZ55Fx6FlMgAb3ji29eLgsUC6qXJQRLE9yHlpxPsT8xbcOO4jziDEMQ\n" +
            "LikNM29eb56/WK/X682btlFuD5ITLsIr7VloPTavbr8wmMGfA1uaDWPHDSv+utlsNhcfVk8fVatV\n" +
            "XT+owJAhYFQQBeb3ZsUtYKrOmZP++HEpc5D9cAPgraIU5lg7LinNOCre9xOQbHcaRM22ff7ifP1q\n" +
            "E2014Y5/0kocw4+9JaBrBiSc9dibkXlZMBOt8esPRLARsqdH34469dgztj/2Q12py8uXJVH16SkV\n" +
            "iIZn/0JETu8lXAOGtAFkAWB7pS6+vbx8cX6xbtSVshlQrSe/G3zDmRfEX3ya8tpZH5BPkJHu1Yh3\n" +
            "UqFTr7hV3Krt9s12+92m2fGpQfWgKgCiwiJK2m0ZsAmc9Y15P5r58SmgPxFyGbpF2r1cjOQt5uiE\n" +
            "CMjlSN8zHbWNGwNdfnN5+fU68rum5RLWogSEH5JBEjR6EE2MTG6dHybJjQtT+ucB63+2GYbZgBak\n" +
            "DTavtxflZrWqaVFBeGA/hfp6LePIfQgAhjQYRKrlzZvm4pvLy6/Xm9eb1uoZMRLhe5pHQxxgGUIP\n" +
            "PaWdHf0YAgxKHPUpCJJ5V9cliwoRCWjDmzdK84W6aleP6lVdV0sqCicvshzfQc5jnRonfxCgpR9d\n" +
            "CnS5+UXs/ogoir3t+P+A/Dxx8NNQBMwJeBi+jAEIuvzj+vlXF41iLOwX5R5ThXV7uKMuoViuzTyD\n" +
            "KXmke9esTRAmmak3lTf7SET2DmANgZax/b7dbNrquLJZxWOihEvjESMAJAkG7fdKMbe79ny9vfzD\n" +
            "xXq91uzNBwMHsTkQlNmMaPuzafRhM3CvTsvfeAL75ddKJTZot2qrLs8vN2fPnp58jCcfVWRTJxNr\n" +
            "SBjtVACDZIYfbpT9BMN7LIVdfZqEfL/hx1klIsWfwy8dJkBUgBaFulLPf39+8e3WrTAUeYm6BZ8T\n" +
            "pSC7kEbaAXvuncHJqXVsAvI8BuEOyGJU9r0iwiBim+XwuZKxdwJnMmDQXqnLV+tVXZKoeFAs207N\n" +
            "CiAYNFfq+Yvz9bfrptXrLWtmZiZBMBRlKPe+O5GESPjdU1HW4+DFCH4NShG7rm3ZhiXjnwB+8VPh\n" +
            "/DXTbe6i/rZCbnfATn35u+et0nz9pK7r6th+Vo0M0x4QE9A7UXbmUz8ISQAkNBv8t//w1/893Iz3\n" +
            "cR4CeAcAeAfcAwDcs/8ZGOCd/++erSgfHSIFju7h6J77UxvgnQaMLR9fP7rXH2MNBkMuyBiDfwcA\n" +
            "/ncIQZAfPL+4fP7VxfZqhz8D7lknlQDQNQwgCHNP4J4QwvWCARH6YoB7rjC/YwMj3hkDIe5B3IO4\n" +
            "R4BhA3EPbGDewbzrfuNdOj3vQQgh7olkWMKLevRn7pa4JxzdA+6BDZt3xsAAhoToGjlRVfdF3H+2\n" +
            "8WEEhBAGwDuIe6D/IITA//fv3CoW/+H+f1d9SAUd3RN0T5h3Bu+MuGflAwTAxph3xrwTAIwQuF9c\n" +
            "qd3//L/8b+f/z+XO3P+/X77hdzD3CILMO2EM8E5ACMfh9wTuGQB4Z4LINe+MeGcs57Nh8Wci/lJh\n" +
            "KLpOvUv7GCg7IPcg7gk7egZCIJIX7yD+Hca9BeLPSNwLaoDAPcKfEYmFuCcMiP8Lf/fm6k//73fq\n" +
            "v+zEEYnF/SNxJMQR4wg4Mu+OjIF5h6N3AvdM8qHfpQ3uk4h+hP/YSRc31Ma8M0f33N2je+LonrGM\n" +
            "YyZq7o1GmAnv/NvuDRt25P/TdobjsFUmA6sehIh0JUMIhBTRIh/TgtqdjfSw3mBmZrVrL7/ZbLdb\n" +
            "8ttauwU8q1b4hT3Ie/fbuBWJorA/v+wzfNqs5JgtEb2uRzzQm7MlR/2I40FH8e+9Hyp9qhePqIGC\n" +
            "WW2b5qotjqtuKpq48UQSuO4OL+O3/Pyr823bSiqabVMWhY3kdYAiELYYDxe3uF9kvD92SBP9ins0\n" +
            "XmzPAPYfDCgGQsJFG7u1eb1VO9V897Ku62pZLkuqllVdFBDSZklBONb1phROl3Izfy8cnqnBd+rm\n" +
            "SJxd+QF0Ofze737DMeRvz2FmTnNjq0lefLs5v7xoFKiATXdBfltbrD8PiJF1ZYnMlGLjbeB4t0/q\n" +
            "C5hDh2VARuqjhn823f93A4ofV8zrP23qR039oKSFi+3xrn6CjYu8DuVqVhkAACAASURBVK8iAOtv\n" +
            "Nxd/WGsj1a5t2BpZzAGuD23KGVYZs19Qv+Skr+euycd6dZCQdeax8ldaxRffbjevFS0KElx9UJ7+\n" +
            "bLX6eFUdl4mNdmuyQmTuSTNZmvBAHVLHbLa/dc+npV3UDtJ2cRbEO+VC1rbt+VfnzWtlHTwKTIjB\n" +
            "K4IAGbBgmOxwBuMwvZnLz8kmyu1p9sOBwRYcXdzGe80RPjcvZChHg/pDi0PaDxg0W9VstzBP4DBz\n" +
            "gmRcW1OMcM2aLS5NGtgqdf7ictsyg9orhih4ZwEOq5GRDSWI238HNK7KjRrcQ/FB1sqII+w4+kSR\n" +
            "SAznKXnXIDMaZihF4O0bxW81iEhQWRARyTvsqWvMKM0NjctKz3nP5hh69Mm71w5GlSj3+Z0Srq7a\n" +
            "Fy/OX1yurfsKIDJOW7NRMW4UhU+AGWkB5EJ+AzQ1QsODDVzh8e8dqQYUqwlIx3C+X8D5yTLzfg6l\n" +
            "Aoj7lwUA5p1mhiQCEwxwTQBrsBTknH+CAOIdX3x9cbl+2e64YYIgn5NDuQUwOlZkOKpub1KXmYMC\n" +
            "zIlB77o+WvsujMPYiUB7yT8yh1MJABGHXdRWqPlnN6/Vl7+7bBWfPjtBISHuBrr/iXj+b9KVbgHv\n" +
            "NkX0acw66l/sp/r0LkbjoFoSxIyX36yff7VurhTIg8ldzEzXDwloEAQTklSzBHBYVAfz0l6kKPaG\n" +
            "BGPaGZdOr77UiJl8CJSkFCJzEgqQdY9hZnwu7uEOxisyAAQ1jdpsm2JZlL5aDeDI/xDAEbTmzevN\n" +
            "+deXzZViA8UoiNiAhARvIcjD9R5TiBsmujZ0K6zxX2SMk+1+oT6SP6/LI/CKf2NPPPHwo9rpZNne\n" +
            "tl4ZLkQBki037asNEarjgj5+Uiz2NWaSMoH9t6TseM4Wl/cSJHIeJZy/t1j0Z9bmt9e1iez/YMbD\n" +
            "BqhvLv9w2bxR5NxI9iYFkNofXwNYKAXUfUh0ywjA1rfUM95I+G9ueGzTe1dVhBl3NRunVnb/ecys\n" +
            "e8v8BdxwZzDP+TQdrulr6LU2XGdev9ls1mt1pbSXDlIQQPqaYWDXf6Xay8v1Ztu43XVE2x1DQKmQ\n" +
            "hM+9ZQC1cGBgDucXGrAX5ZmO9Oz/7hF2MbY3pvhdQ34Q4bsD4dOYzknMgFItDCRR86Y5/8Pldtu8\n" +
            "B5fd7fp4AxLxz7D29gforpX8MUA+II4cCrQedZJssP5Onf8JG1PYJT2pEABcnE8vNIAEQARjd4PY\n" +
            "SRxkB7vnOkd0qLQ7UdODgp0e3Sm6oXQyw6Lt94NA1PQtIxQVsPuXgjmzBzgABnFv/bhXl/mb8fyb\n" +
            "zeoZlkub3qMFs7TjSrJlIkHrK36+bjbKhtMRwCQAbigsjyIjcTy597pYgP69wfo/3ikKtQUcNOv7\n" +
            "GICImVYNRy8BU+N22h1B4J0iKpiIgZbBf2IUDYFWjyoSYNOW1lElSEKO9KLfkOxpFHoENMkUG1e0\n" +
            "ZxIbHZpKwr5hWiSP0YGNkNk6/bSWndeNYaSE1gIQ2Lxp1q82zU5HyedyeSkEkCj27N7ovTnolC4G\n" +
            "QOHb9z6b9cF0/gGStqdGawEYL318A6wsICIyzP4i+XRD3UQf89jllHnqMvMN+pijxNMmEJusnezw\n" +
            "f5FAy7pR7Ymxgf7ezhISR0Qgpfji8qLZNrDovovk8cCYjxCMdjrGulLk3hsmPjYpA4e2jQxCvrdD\n" +
            "Ev0/k423WSmcoz6YvygcnGkAwxtucElPjqlellR45N/0JCyFuTdf9z4ARJjJpOPrRM+155+OTMQ7\n" +
            "pNjyz4o9aWGkQH4eaAOQ5B3W683F+qXaKbulrN9ajF+JKpQuXb9k1jZ5e6ImDMwTf9EpBS6TTMge\n" +
            "GcNXbgFkEkR+K8us4NZkgqLj2InjAEc/TWzW5k4ZCDq5IAZ4p5rvNvxRaRPsOc4HtW2LRbF+tT5/\n" +
            "cd4qdshfND5B8RmNXOt1yqRSbzgpRfTv/m6Ol5xhFh1OzOzgErtDub1qX367rj8oVz+rCpIakDY/\n" +
            "lZkb7XIQWJhD+2e8ZU9nZcxEInfv1vv5BzTlt+izQbdatqrdvNo0b5RH7CeI05XfvnToas6JuUwC\n" +
            "/07QaCB2bpPnbQcf7M0LNPa9+8zZKQ6ZkNUx0RZaG/0KgUygbl0Kag4JMPN22yil6RgEEEn2a7La\n" +
            "qZfr9fbNVgqymo71p/RWxR6el6ch0jlYovc8kr0+h38mrP1DVKrurrU7DK/X67qg5YPT4kEFsIaU\n" +
            "74FZbkViUiYGcSwAkURL3tCWsOEK8X8Hkxl8YCGZsdmq5ruGMyCcd4wny4uH2wwAkmN642BmMJiN\n" +
            "dfWkZrMrGYW1iMhYyGBIXhsMWOAA5yNBlDuWF4CDGw9e9ru3dOBEYmsEmdjBW+2O250Kgp4BCJJF\n" +
            "2Vy16/WmZSc4FDN2vWdzDRApNjFGWR6Ocb4e7UU6J2gAuOJgYI3sPh8LGzEzG24Ur7/bbq9UsMv0\n" +
            "+1gqb0liYuh8ew3QhUqL6Hb2SxxOsSAYkwu6UwoCUu6IWW+/axqlbPN8INqARvoZFHvpInklx6f9\n" +
            "wHN+jDDHz5u0QO+W9RHEIJOxEsQ/6BLykNW0B20e4X/YTtIBFm9X5+StqKkkSL1VSrVWV3LNOyKA\n" +
            "1uvNZtu4I0F9R7we5BzgBNe1wPPc27q/l2ljzj+U5kzOO+B89CO7vFNp86Z9ud40VweEe94d3bGo\n" +
            "Eejl8Jvs0vwDSeeSAUR2BzUBurlipewxtWNed69+Cn9WjKs2Qf47gzOHLcUUm+vD1BHJ4yHPnIju\n" +
            "htEz4JxL2QGBo+/fxxi5ljMzBJGHKsga6imoQVFhJtI73X6v2DCEZAMI0tesrtTFHy6V4nJRtCHi\n" +
            "xaswiGVz6pCfauFBGvihQiEIcZM8Owy+uLkzLYJs7QdtrtTlNy/rxyu5KJYLGy3xXtb+HNrvmzRB\n" +
            "e0ykiM8MROKu31Ov9s5hOWwc5pxJloUroy5pj50oYPNGrf+00SZibBE95V33bjcus3dEAeh2BFhs\n" +
            "TxpikS77USUT0DqbNC7N8zn5MCEmuMQVoRcGIWS4J7E6Z8PQKuvN+wnlX/gOxq7KfOGwncaVUYYL\n" +
            "KrRRW2V33TuFXwMwtH69ufxmDUEW6IIHh13I0FgE83TL4z/t8YF77YKgtc7UQA8xDTpAJCHCAO3v\n" +
            "BjbCbmyomJ2ZTx6dQLxHJ30GIL+xEdQjLzQH9c124E1z9czIZAmE1dtzp+P/zfdte9XCeLW50z+B\n" +
            "noN9guKVH3YNlO5Pclc6SrHAfi6abg30UagBlpsQt4LidXJ8qji8oEsxHIpOrYfWe0c5VulDcknA\n" +
            "XFTSnm6udur8xQXvoMljHG72292BaX7E281CJwR70JTplSH4nfjDuwmN3Iolbyd2x4nhfYSxiRQF\n" +
            "NYRSzGrzetNsayJZCMjsqZNjX20w/jNJ+kV3SHpi+vUoFSXiAHf9eMl4l9Ksw7aTai3/sE0QypAA\n" +
            "8Y42r7ZqBwgC+0ys+cfh/VvdMa8Ep7JK9Jcahrb8b7EAbYBdblZkF2fTD6dJXF9JQu6MktZFlVDo\n" +
            "cqdIJ8faAaApl2G8nncN87fS0KPu1TYy2u5ZijAUYtBm25x/fem/hQ3mpV4l/T/Hvuxw3gdDCa4x\n" +
            "2WCtmPp9H+eZ4Y7qcYV/4IEXSfHOJ+KqpSDyfDAFAdQyNg1vNk21rLDwi1WswAIwveNkfftns/0P\n" +
            "kP/noDdM5PbsXPp7Nyr3ySRVWz28UWr9p5eaGVSEIJYwp+PEOwCiRFTB5vet8gdd+HPaoA3nMwjE\n" +
            "lB0V089RE4yC/um30WqfgAjhbtJW+A0tzk+RhBj7ZSc2ZHrXqb+j3sN16dIX9ZkAaPZIigDv+OLy\n" +
            "stlCHpM0rIwiUSD/7PgQ9Xg+6oKVxTyuAVE2ICr7lgHFMZp2HOIj2PuNH3B+TlLkUo+6x136GN61\n" +
            "zVXT7tqCylQJ5XEAYL979AemQ5g/FeSjW/Rnc74rGQ6Tcco5seH1erN5o9g4eJkNRtNs2nx4ST/G\n" +
            "j1IcKJA8mKkAsT/tKxOgGueo6G1WG6POlB2zz7317pvRmdwdP4zELPgFajDFbTBCtzfZBxG5B9Wu\n" +
            "1aw1iA02b5oXX1+AoF0GPpuTr+sojcza0Wif0MKuSePz3vhdWGPL+xygq4Mn/DD60Ut0/mwlnUkY\n" +
            "FKJYoQuIKSm7uxHc7Hiz2TQfP6mPy8lmxTSybeRH8Bo4ipvTOQCB/cZ/b9/eQVk6u2KDnrNhZv3y\n" +
            "25d6x4hPlfE01Gkz5lZ2AuXak6w5GGczu5yCoyniH3dlKC6foIBdG5ye35cBY9rgnGlhwrx34Ein\n" +
            "BudgLWYGQYPbnQqHjm2+22zeqHKBFgRWRIXbwwtNveb1baik9jH+7oIjsoaxSW9Ni4C9FCcRTKx3\n" +
            "oAsVS8HLyDLy5BCHvtrvXTzMvH6tnm42/KCgRbl/DR0HledTNkb2NhRq2uPkAzLioHc++c0OJLYK\n" +
            "ufT6kj34bfvd1p48zZ1dvMf1HVsBHRjuw/7soUsSXdCbs/lFwvDRtp9oAYHnW8OURryyiKz0mFxm\n" +
            "kbSF8DPJsms4as7ejkYv8gt0VsbUXLEVxsHzwuvb4e2uKmYDMuC3zMwSUIY3b7aaXVYrNkQLCruA\n" +
            "ObGqcjp5NBSO/8fb+cMovh3n92RWl96jh86kkgKuMMD+YMJEjpOLkuZm2zZXioikYw2PKYhE7ozS\n" +
            "IWv+XR17FZMY9VJ2RxruUQFm8nn+NQIQyo+UZAOiYrttmqs2nIqJzKlY1AuGy7nr/IawcIuho+XF\n" +
            "ojL9Z0J5GpigVgfpOhs0Q5JgCNLdFCHf7DhoKeqyuzDAtEx6K1izYZPMUGMSfv+c8AU6ScTRQueq\n" +
            "snsQIMA73X7fto/qZofNVrNDCqzrlDuBaEVAVpnKNDtqWw4UuCHnz0HIh2XGQMphPfPXUsO0cMaU\n" +
            "Bm0a1ex0BakBF+drZPQucqr+eMtvvJjfUBaknyZ67VCdnqTsAZv52gZ1RiW1xUikKO0eKQZtNg2z\n" +
            "yxjlUkR7TqNUu85xr/V+2TcmYVgUc2ZvmR0s3dlpSuTdCom4sazFMs7QaDsGhGOF+zRmfPYaRjQQ\n" +
            "EmmTOiCNAI422/U7ZNOi0IJgWALMUDtmw81WN9/7Gry4TJKRCCS7qmb25SAaq2FoDoyUiXKxRD6U\n" +
            "bOH4e6RJ3BKjIHEwuTylBCYipRzst1F6+zZCExy0DD2GtN6C0kNu+nWGHasHCYWREc0s+NmzPQ6g\n" +
            "/pN+tYdh6SxqQICV2rzZaGabO82Fx5oRwGzQfPbZPm/cTofVp4augwYS6CFFuzrJwjDQIrcM+n+7\n" +
            "r9fx7QjN+ZY5i2A4QcKfUhDA2jAztzu9/tOmuWqiUkljevEUqf+P+3Ap/J8HrUvTsmN6oe5UobyQ\n" +
            "7QyooBpk1PssjXyUsK3bMLPSSoE1SHaHyyX178GDrWusg8/80E2mCc7ObcYcdUBM/BW1au/FPWmM\n" +
            "5yhpAKAtiOJSGghqVLvZNBwvyM6Xbp2uaUy1x3jj8djD+V18zgj8nns8vDR6NtxMlRFQJzhMAhrZ\n" +
            "An7qYHJNSOFDE4mPgTbr+XPUiAgl3b+eDTTASjXbLTPTgryDI0JPJtlyapTvDpTK0AzJ0g2fd/gB\n" +
            "8I46v857T+fE08NbnRvQI3/agGOvvmvejZaffV0bY++DrQADACLP5y69j7sVRxfFrzkojXli83eD\n" +
            "JeECbzWMZKC9Uu1O2XvWke7d6V47GJKIIIARSN+fYJPa4FkxkfULCAcXuSb5fTtSSN1FPds229/d\n" +
            "waHukLgUk++60nudf1H/7WmBXoM774MIPU5Ruk71YPunJJREzKxUa3VmjtzbvUiKQJxCLTdEuYcP\n" +
            "TjgChg/mGhC+JsUCuv+sDc0YXY33eC7hB9CTZlZKM3NyJsXYs2MpLUR3q3fxfdP+lwzbemP+H6Nu\n" +
            "vBjb7xveRSYoonug7iyKEZbOUsfnvUk2Ji+yUIIAjOqV19Acb5vpNTitx6sA3J/Zvdk8pjanje8M\n" +
            "/UzRaFdvZDOTs97Z7SxeEBvNb5nBZIJHI/fqacqKy5kkfAemFfKs+MvpfYHc+AwhgGnhtbf7vp1s\n" +
            "gB1atWVmH5Mu0Wn/h1HMVhMMld0yky2zRxcQ4Z/Mvf7pOnubtZcyCkaHKjGoBKjd8WarmCN8GwjY\n" +
            "vt1W5UMvyG2An6bhEOydqTHWhU5Rd2ktrGIPF3LnV1Syqb58c4HefiGB4GROrIC9bdhHnCrqiYMj\n" +
            "b2tYQeZFoYE2DGbnfQybeQVh7JySvMk9r81z5CyAHGrjZH2U2zu+OCbHu1rSzxFH/pDDlWYp6hZU\n" +
            "6v5039yljPOuwTyNAWbpGR7dlREaBRHTU0BGuhN9qfCG2+J5M6mLEHbyyZ6FShJggHe6uWra4FwW\n" +
            "nYu1w7QjUDfjv5m/cAXLFkDkhB/Cy8nMMJEXEG4oGSCjfQQuAJf+wekp8VfooQwG/ak83n5Gv83T\n" +
            "/Ur+jF/hNX8iqXXLrHvFyC5EXgvoB1MHbSv7IiSv2095HSdjXGT402STcx9Cdmp5XYzDPyCEpIxJ\n" +
            "ZlTqTw+XJzoEBVnNlFJGmrInEjUhmsl+0e0Y0/P2qJAKqTH2L9IG8Mk8QgKPWQrLrLw9wjV8Kv+A\n" +
            "AdweG7ddWV2ppmkRo8ode0/OtuilvSsuvjX8B3SAnNgzaSjLZrH3XhAJAjPv2OLANuUL77heFqef\n" +
            "nFSLGF5ycQFDd+MNyE06jxHkm9ojAQjYQxDruq4/XFoh5ZN0WAQr6antI1KQP0mXMD2G6V02fsug\n" +
            "/4/DXPZyZ1hD5JnzV3J5ga0tE5o6kTSpj/V2AG30e4TGErfppJKhp4VHl+tYoZ7U/OPk9zdPnCWS\n" +
            "n1Fsn0Fy9maX+ia/gX+K0hHs6RUyvuNQEFKM5vtGKQWRHrkxFhwyQ3uPnGrjj1hA2HAaeGefGuzx\n" +
            "iL+TgMMjnTltg3+ZGXVBf/tXZ/Xj1T/+r5tWba1yDhGWldty/k0ojAAzHRf1si6LCuaFW7EsHAAA\n" +
            "/mBv0YVLIDIuIsF3yHvDb+GEzSzq1vzuWud+F97tku62zFcuYuCT/L8c3yXhgv8o8nmmE8ArTVa9\n" +
            "76QYSUp5/tbf16MAllfiBXS/ej7L55fZz99/d/fnnVDPI5L0w6BVStukcdEHhok+Rg8q29eqOWac\n" +
            "s+Js6Nvw8Vhq5DBqG5EAax4zk0B9XHz+xennvzgDqK7rl68V7w6Y8GOUf37GJEtWzngpDspUz+ll\n" +
            "EO+Mij1/SdBUuJX9CvNmf69THOR1FlnwzQ7fhV0ozuA7DzV2ZFQGZ8I4cz095VEkMzD54RrO8Pbd\n" +
            "vl7ekHIbZO/OMBcJ6td34w9hv0NFwFBqDYkANtLuJ1M77Qywvg82RFbMfnd2Xg7tUm/1kU+2R4Yx\n" +
            "NGuzgJYAwMUCaqdIcClIA/Vx8esvzn71xWlJaJhXy/K5V6WD9X8HkmAGDXY6eoFLRbmQRHbiao7O\n" +
            "tOxgDm/tc1BiMxFWNBMkix/pvQjof9PObQlAUI+1RsGuHg12UvcFStckkOk7jDpLpEuLZl8dFyMY\n" +
            "bpVu2zZMV57a0nsYpWwfO93vjMQwJ9fQ5XDDQGL/uLRGUcjRIwATfks22q6f2liXqXOVUxJtZpdW\n" +
            "7qf0mFh5BhS22XmIOL2d6GwzhHngE+ZiQWBuFa8eFr/+4vOzz04loWUmQdWHVbGg1nN/yvSHS4As\n" +
            "z+xdeEMBchbpslrW9ROiolWsmSOeip0sfbff4M99R5sNybkVKJyrkdhB6SPd5w/SR+TERzYoy/8I\n" +
            "5v3oWPcSt3pNM9b1uoUnGpmA7Cr2x6t1s3FcLMa2xgy26vFg1gtwcAaN0BYbz6ONTNyDAvIWTj5n\n" +
            "4Zv0SnbxNBrGHqQFZtac7o3xbQSQpOWf09Uen/Qw22E9wkUQImTpGEJZwy4I4p3d14XqQfGXvzz7\n" +
            "/LPTckFgVYJbg3pZ1A9rdbVue7lxbrBsHkTZUfIftzyuquPColAyZPWJVfqY/0VGYjnF+yZo0yFa\n" +
            "z9AQi7c5Bu1MdEyefSp+vFeSkXbwBksrB4eoPe9wbz2TrjhPOY4bdcndjP/vVI0Yof7+ubFWMvht\n" +
            "gtl42R8BTiFgdrh092hw168YhIDqxWfvBaHTqfpuwXEn1VgDIdU1yHB5XLRXqloWv/7i7PNPT8sF\n" +
            "AUz3gWtgh6IoV4/r9bcbYnConPyyGa8YXr2EV7mnuzN1y6J3BixU6DoZeA8fFQuCO63AH2rqh8Eu\n" +
            "TL1FntxYdQOYtjzXpBidCcM18HQOBUECMXTdGegFvQb4Vg1xdg9YuvAwe6UP+MdNmt4bYtDpR2G9\n" +
            "7IOayKsapsP8p7JiedImU/DOFHOgy+GnBx24sYHRHQXmz97N6Crd/3W7A0QBtCG8xH1vk6r+/mJo\n" +
            "e9yP9Ae5zZWA3V8XKbUJ2E6G+lJT9M6Tts/a4z6XFuklA4AlGIahcPrR8le/PD379FQuAN4SEYNb\n" +
            "09KigqG6rk8e1etXrWJu4/6AHcgU9szFiqX3veVD9/dSmKPdnyVMWy5kVaj6gYTfKqk5Hj0KgRUx\n" +
            "BYDtMEPFdM9mTP1AQ1vGABRmArMhH1vV9/P3HITcuxu9ImPV+y06wwzO3WmLaTvJppPt5gaxYbVr\n" +
            "k+m3TyHqr4WzuUwKeYe7+qV4ryu/6f7VYsJlIaUVosJtmfWHycebcEMi10hp3G/wu/wgqbywaSv6\n" +
            "C1eGt3zomM8m5LBAAiBYeqf96bP67Bdnz/58JQEYdmkIjkCCcAQ6ovphXT+u169bHVB0thCjn99E\n" +
            "3ZoQQ4xRw6iHWWBk4Zr8ngTwrsUCgCaB1gYmpRPX8WrwsQ3GJDk+PG3qkI1DDbHw7VFIN9h3IoZw\n" +
            "Y7uZogcxWC0mWE/Dg0OGyubAVZS4gdMW7pG1QRg5U1xrEwJ79uXFSZfufQHyU1lzb0N6wtU38bK7\n" +
            "hBx9ndowCdAREFLNOGbIHX2BcQg63A1zlNA5rnKAWX+/fHLmH4WSJMhuhHETxHD1YHn22enZZ6fL\n" +
            "4wJgzSwFcGQz6rE8kgDpa66Oiyer+vJyo3YbJFPcT/gBjASDGPByZSJxMAFfdRUOzHVJpNlt5vXn\n" +
            "FyUNiIclS4kHRKQa8g1mRY8bYzKJCIABDyN2jJehqcQkIrgkPDOot1DH0WXR9cQOFdEk8R2RB0FR\n" +
            "PZk7QhMpM+5EBXifK39QgWY2VEBSSJY8YlJOTJeE+jOyl2LVub6sIjC0bxHeYjURhiEykFD2GxcL\n" +
            "Wq1Wf/mLs2d/cSql1pphbBpSZs0QwDVwRLhmMJeE1cPq5KRq1WZz5RKB+YT/NGwAEKbgZNTKBAX8\n" +
            "KSHtED6yZ41JwJ1C0R/PbDICf6VLr4TUNg6Fox8zW07Rv/6Neadeh/PFT8diYggHdC2c52MNSkRs\n" +
            "VvQCB0R/WDQghd8mMOmR7J1VP1dqvAfKM/8drO32+wmWptP5Ee15inEB+6+ksijSXIhOxB6+toio\n" +
            "5uHgJp/N73KBV3e7wk4ShZRvmiEJq0fLs8/Ozj47rT4odau1YQhICQL4OieejlAti9Nnz7ZX7fbF\n" +
            "Zbtjoq7OfpwZognXNfIQiz/wQBB8vhINlguqH1fVhxUtSCrWb+15vtbXnSpBkzOSMMiSNnxqnkLR\n" +
            "+dWC1PDnhUS4vT8XkPsHNx1AUyBRaGcXQTRNeY/jJEBjocGhW+2HoJ5pZgBAHJBpMyo5zwvIiI18\n" +
            "X6wX1Wf9NBpMVNCCpCAQR+nrBkquffUeEU5AunM2POUhw0T9jpa45BR646O+CCVRRXj26clffvH5\n" +
            "6vETftu232+IqLxPbBjWXX6fSJBiBeMycJAgXCs+otWj8uzTp23bPP/D1u7/YQNC4TGIaNA6nb+7\n" +
            "3nHIXvJDF83OzrNVL4uzZ6v6YYEj0oaZfckggzoOjIYuYpWpA8IGNnlMjMGRONnCBgCH2Lv46Q6m\n" +
            "hVNP4qbE273DW4ZHjyQyUZA9YTX0ug8cdC3njEUgQARaSEnSD9eUlM5wyqSUGW7y33siXp5GBLTQ\n" +
            "JvElzMnDO8veMMns0QIyg4I6yMQFfgiWAmR7yB3rdljRAetM9w04FtK+PcNPRN3rGALY+Wy24HJB\n" +
            "y2VVHS//4X98+uTRiiS13zcQVH1QsAYbxjUgiCRgoLSCm3OEo3BuL0uB1c/q1aZev1Z2a41Vm9m3\n" +
            "nFKYnY0CyLm7BJFFBMJM7nU880W6wz9CcJQUqD+s64d1eb/Uml3KFqJO/HXTl5Kas5De8O0pgErJ\n" +
            "nzzEIKKygKEpg8IfZJYIhRDSnxWLxr83bltUMngBk/ZPSFjXJPZj6ww3ElQQSQGYlg8JvJ9ed9Nd\n" +
            "fQDuEuq3JKa+6wjNUhbidT5fPswDDUhJUArFcUFE05+z/5b8iFhbvQvX8ZCye6nfpuqmnRcRzMwB\n" +
            "VZYCRUHVg+r0k9WzT06qZVWBAYbm0pn3AIBri1N6tOmI7EV3C349OUJRFmefnbKRz39/3mwZZFvo\n" +
            "pycjrHgIS3+Ib6USph1dKHLDxWyxUrekl4RlQSd//qQobCJzl38ahvMYpxitOaGRJmW4PFthrHPF\n" +
            "j7qv4GSQU8cmlI7hND7ISEypt5cs2mPKvm3EgrFjFCQXxIa16R/a1Rc3Yzw/CWPJgSf+Jib5yCPp\n" +
            "WX2BY+8KB5yDz1ldA4BAKctyQQ0Qrz+zQKPs7O8l/HMT2qPHcBovCbs3w54IiHIBIqofVCcnqyer\n" +
            "ul5WxXFB9wmGe7sU9jUsVS+uAXD9YfXrL8pyQb/514tmu2WAiHjHNkaYBPEO1l/o28ywhi5aImJW\n" +
            "Hfg00uv4QfuDvMZQHVfVB0REEro14J0VLJ7BkATSJ5DjDShtW5d8va/hw4VRhVe7Xdg2lXOXmIyG\n" +
            "hl6YXZmvPJey0Nxor2MgyTeViCCob8zOp/HCvUNxQoTvbckvmek4mUjJuD3/R60cjUsUAKRbMwlU\n" +
            "0PKD6uW3W8DZzF2wzTQomt6KMRtydXuZ7YuQoGjLKpOg8phWD+vVqqqW9aouqmVZlv50cMNaD9CH\n" +
            "o+n+90kCEFRXxeefnRZFdf5vF5ffbNorVQgql8v6YQXm9auNj6uK5QAAIABJREFUZpQEiIKZW2YS\n" +
            "BRGYkdk9NgFkArSIgA+ABOoPi6IopHTJ5G0GSzZIt1H4qsJO5+leZeW7Sfbtdgr24MBVm0HMmjah\n" +
            "bKSscQezmxS/OJRGTBgGpkL6nPXKGJEItAhC6RA56Toyldv3DmP4RxowLiRvezaQlVIzChLAwjFG\n" +
            "saBqWUlxqQ+U30CYXtGUM2ESe5+ZAOABHj8dy4JWD+vVxydP6mq5rKpjogXB8upb5Vw4R0SSnDIf\n" +
            "qPdnTEdJga5NmrXh6oPi809P6g+Lpx+tN69bEnL18ap+VLNq199cNlctkSyL5fr15vzry0ap4M1K\n" +
            "lv3Q6xHXtJvuHsOnRVk/rMtC4noLUZAoOxPDpK7QCMEehsFkSORFM2d/ukfis0yt/mUrSbFPBhWA\n" +
            "IN55yGBoNUyvCogWj15HUosjw7sBIco+axmEJMmS/Ka1LlhwolWdCBs/VPL9UfS6DNp/QH7OveN+\n" +
            "EAmUi7J+WBXHhb5SA5B/ynfaI5c5g8M8jsxqC+ALlMdFvSyrD5fPPnlSP1iWx2XRJQ7kUtpjAp22\n" +
            "wpoBTaLsVvsJzvfd6RWz+oi0u1Ak6odV/bCCAYwTN1igfnAGAAYgOrl6SiS//Oq8vWIiGvAGup0O\n" +
            "3ZUcpmWIBKrlsn5cF4U/flfAWqrdFO8WZ6DvS99Hh06D7I6dgMtYqJUtVFG4jgRzIHnjeLaVXsMm\n" +
            "Wzi/q85aFG6PEFFRLggUY1taG8gJ5M/A+7B+TM4HIG6uSATV5Xbk4G7Lk4T6cf10Vf/295fTkWxp\n" +
            "MyxFlrBggG2ssIv288tgVVC1LFZ1tTp5+vSj1fKYgEYeSVy32kAKSUcunSNppA0gHCWa7J5O2dE5\n" +
            "Al2nzTOMIwBMgl22VwDXCga0AElijZYZhPpBcfbpyeaqOf96bZOXdh7QOWQAl22SygWtVnW1LKUN\n" +
            "tBCA8XsoB8L94C+aAWJ7BrrdszTYkW1ZXbBPfWefBTznV8vl6vGTtm1bZc9NDl6J1Bs/ROwO6chB\n" +
            "Yq7rgAEESDqbP3DyXF/YZOuyevd7QPvHac+aP82ZFjDHrJlKQGulvqDquFg+qBiXqf7XlcxTqpJ1\n" +
            "J+QKyzMgQfWj6unJycmqfvLRavmBBMC6ZcOlAK41ACl9kM/bloX0UHmnH7JF748AkFfsfdP6GoG1\n" +
            "Er2l4NY0P8+ugSNI+9Q1XNpPO+nfMgD7bm24Oi7rB9XF5ZrjqR8o2obcHxCHSBFclm5ZP3wSY2Zs\n" +
            "tO01Ytd9FqaOf8+efxwJwIRLs4aJ8McNhlcYFEWxevzk5Ocnm9ebzXajlMpgEwOKROTQ1siX58EV\n" +
            "lyZ4DJaPeuGG1HCXqyI4NW7Nq7HePYH232JXX1qjrYh3LQQYRAL5BH77RNcQ7QPcgbw90jZrYIBP\n" +
            "gbNPz9avm+e/vyQK8S8Ew4XwZ3gAANnkqkQls7aRMiH5PBExUAjrysLpz5e/+uXp6SersrAplrf8\n" +
            "FgBJFK6pQtIRcE18DW0gqcQRtVpBsJR2WDQgcc048ilxroEjH7EYAc5+szBraMf5cqnfMkAQ0rs2\n" +
            "NK4hj6Cv3Q5wPgKBbIAgeRe9FLxc0ufPTjZfX5y/UiXxxrgNhcUCbKB2Fslckh1daAKTUQCYCrXj\n" +
            "gojA1QKff/b09GOUBDKMo5V1ArLZsNkAfZ+IG9/8lIoKh2yZBsxMRCDwjgEL9nmveGSWe89rNw3I\n" +
            "EOwJAsxglALaMHa8Osav//rzs8+etkq/2PGWeMMAuA3Bf4hAOOMnCazJbUNuVACSu6yesVJma+iA\n" +
            "QCdbGSDBbnMnupphiO3BDQwiJsElUBJKQgkAmgBmCUgI0oYjvX+2guwbM30Q5sGJNvKfUo88GpDw\n" +
            "G6TuPISSJB9epkqi+kFVLi5bu8OHuqmUgCUAAKUagAqH5jtI301uwSCqF/j8i1+fPVtBcEit7Ykh\n" +
            "QEcSQPtWs9HlopSStGZmLu8XGtCacQRJBAOtWcN+VIIEDHTLknooFAPQbzUES1lqjabZWk9iy5oA\n" +
            "WZTFwiM9kuQR9DXkdTSPBUgSwK0GgGpZ/+qvPq//uL345uXmtTvReLsDGRQL0KJQu24Au5ltmAgw\n" +
            "zIbLB1VdV0VR0FEaOWO6N3Ykxtje3k0XVbcG2AN/FDha+sKrRDSdbCP9q8nZAmDFMFwsCgBgloTT\n" +
            "z85Onz0tPlhCqNXj1ebVptmuG2aG8tVywEG9QAlbMJ3RZ+G36JR3UNFxONItjL1PmFmm0mz/AEBE\n" +
            "C0mDo+sHFA37IU6rG5HfU2j9aOGlufrzbE0e7b4Ttp86wyTRMO2ijaJA/bgujql9wywAu4na2P0w\n" +
            "kTfYNZUgQMxqxyRQFkVBBQNsWLu1h6v7JIk0+7PAExcdu9X+filBWjN29oxQNA237OJq5EIWRPaI\n" +
            "W21ISmgNAJIKHIEAvgbQbUCUsgKg3+Ly3y6//PJ882aj7RQUVBZF/WFVPaiWy+JJXRfLkijKfWRj\n" +
            "VL3tIAWhwOmnp3XNLbcvXm+JaPWolsDmTdPuGDtFYoneyiJQ+AQk1XHx7GT19KOVlD6S6XrgpMy6\n" +
            "CUaIOvkLh7cZ5Y8hs6cqM++4IK9VRVV1x64kGLsfN1Yto1zg2Sers09PiuMCWhFQPyhPfr5qv2e6\n" +
            "amzshscpHUJpa+7WefeuSOh4Plc7D2oKr1UKjABvyXJNAoBkuNO+2IdglYsibx8F6vsjyL0RdwqW\n" +
            "O5qXQNC1Z5S5ZRZsOEAWpBPIi6CcGWYkDKTVQgxDuBib+kG12W6Sdd46XUTnxneHyTPUjguB058t\n" +
            "T5+d1R+WWpBibpuGd7z++rxtG8119zmvM43RmiFIygLg5kqdv7g4v1xvr1prjRcLWVXLp5/UJ4+e\n" +
            "lB+Q1gwbluhr6y0U+i1DFC/+rxf/+L//plW8elRL6TICtW95vd6cv7ikgk4/Xj19tlp99KQoChho\n" +
            "Zrnw2wENSwG6T7q1A1JWxyUJqori17/8vK6r86+eP39x0bxWIO8LdF/Hn2vMqI7p80+fnn12Wh0X\n" +
            "Dp6I2X6wZE3jZN11ASQ79hiGi9gSXgDMpSAs/HE6Lgy+IJLShTay5tZ7ZAhASUQk66o4ffa0fliV\n" +
            "ElozEcrF8uzZaVVW6+827a5ll3gUfM0OfLmG2rWtYt4pq1Zouyr4Gcgi0yuv21KHBwe5JgAwx6nW\n" +
            "DOyhknHGLpsTKRN9hHT+j0mWoQfxtiQB+MMvU4phF9P93E+3SuA5+aw0gDcQpVWYLf8vUH1YlN+i\n" +
            "Za+YCelypEZ1unB35kLgyc/qX/3y2dNPnhYLgiQGQ6+UUr/ZNZvXGz55Whb2K6U8DwAk75N+y8zM\n" +
            "O778ev389+cXf9yUCyqWlQ2w3bxu1398cXm5Pvt0c/bp6fJBJYUF89gyGwHeF8C4hqTq5bfNb/7l\n" +
            "t1rx3/7V2dkvzuxcZMNgNFfN+eXl5dcvn391ud40p59uz549Wy4rSUWyaRQA2CZTZGbsNAFEsvqg\n" +
            "WD2qK3pWFcVv/vOX6zfslzK4xgiwUvUxnX1ycvbZ2ZOHSxgmwY7zPf+76LSgEgO9Iwx6RLCpsnsJ\n" +
            "vBlxKL7hcoHlw+WTxZPivnNhMpiIygUVVSmFYxY2Sr/VNriSiOhISqLlcVGSpAWVArhW/gRrRQWd\n" +
            "/Plq9fPapnuyzB8fNKB2Sret2m1b1aodtzvevqVWtUopzSCACOzXfySbO7ze0QNNs2u4v0WCAKaF\n" +
            "pPtlJsxhTHXq6nxPKz/gI4L7ibmmm/NjkcerwwUN6JJQP6iKYtNuFRsGiq5wSrwDGFWBZ5+snp7U\n" +
            "VQEYpTUAXcqKBK0e1JffXjIzicKLjwDRMwDoQmuCoOb15vnvvnz+1YZZnf3y7PTkpH5QMwDm5qrZ\n" +
            "fLf58j9/+dt/fdEq9eu/+tVyWYG5LImDJ//aG5cGrdL//M//tP52+/d/96tffXFW3ie+ZrtGaabq\n" +
            "QV0/qp9+fHLx9eXlNy9/+6/n7bY9+8XZ6qOVBIEVCHREfM3MDEHlERrFareFYRitd4pVUy8reibX\n" +
            "fzxfv946iwkuHEUZ1AJnz1af/+J09XBJR2DvX8yPYjwpTbYEAHc2sVPamV32IatiLLBcFmVBVVnW\n" +
            "D6v6cV0XBS1Ku1PDyWjq0FCAgCpsvC/vFwB06zKmleTmg900oZkhIO8XEkyQDNLXgfO9SmjYYjps\n" +
            "wNy0DFbUfK82rzeb7zbNVavYIiDQNt+mDf0WRGGp98owoDE4J8aHGyYXneKUc5e6OnsSIRrh90dT\n" +
            "S7WY+GuEbmX57/N5dAkbya7/AICyXD2u6g9LteNGeTHp1doQF0V2lROojunJ47qqKjiF3NXNOy6L\n" +
            "Ehq80/bwPI/mcKQAk9rx+o+X//TPz9evNicfLf/2b/5h9fOTUkoA+hoAigfF6qO6rqvn//rl+pvN\n" +
            "88Xzz7/4y7patlrZ2ET3Pjgw7PLy8uUf16c/X/3qi19LoVnb7V6MI8gj4Brlojz9Oa0erp6cXP7m\n" +
            "y998+dWlMvj7oqyXSxBwBCcsbK3k7AvLG9KfQVYsqCBpuZH9UNs2rB7R5784e/rxylooxX1iu93w\n" +
            "KJwR7oC3MCL75wKDhQ8rYvZRLnj6F6uTj1dPV6uikJJQFFRSAd5CDE5bsgJION+Ywzqs9XENaUNl\n" +
            "QCTB1wzTAtRlebu2EKkPRvZ+qq5xNj5IACiWkMzL1SOoj1fNttk0jU0EcPGHtWq5UW27Y97ZE0o5\n" +
            "uOsAcrqgkS7Lg3NGdofE24XK5mLVBqxzuViAPML/ntkeQJJETAwAv5Tyl+8Y3u+8KcN3WrlroVod\n" +
            "5gpfc/WgPvuLk6ZptWIQKaUAj+eYriq2zici8no4WQGgFWRZLogkAbpptvXDCpJwzS23IB+DdU1s\n" +
            "5Pnvnv/jf/oX3vGvvnj293/3t0SQRwwLLFuHPBgLnP78tFgU//yf/vnicn3y8ZPKRsvZxfAIfE0w\n" +
            "iu4Xm83mt//6m/pB9Td/83lZon2rbARBkuj+WkOAjuWz45PimP7xP/7T899dFoL+9n/4dX1MHMIH\n" +
            "rp31zoabbUMBISfAsNpxeVwwwGy9U+TUe8Kvvjirl1acMr8F3SenSoBxTTiyN1q/9jJbJ2U8sDbP\n" +
            "gHHHkzsfHgADK41tTMRf/vLs7//682JRkLCGG8MwHTGblu4T69YySflB2bZa3icYalvmnYaQRCWR\n" +
            "lYYqcgEyrkESzXeqrJYkwK2fQpLYjoksoNFsVas2MFouymJZkJDyPvTbVu1kVRXSKBKoFrRclCeP\n" +
            "CELyTj9b1Upx833LBs9/9+L8jxvr82MFWoAWpBQHLDmZwF3SBwJQLIgVM7c4yjgLXC9+CFYfX+f3\n" +
            "+iDEILx3D9tnTilM3RjuIlLNcQxCIsS4S+SloCMQqK5XTx5vtmqtd6ogYjBbbVNYY5J5x4UgBrdX\n" +
            "qmWfplpaEcF2rbDGXrtrtQE0AyxJkqT2bSuFhMHlv1380//5ZXVcfP53Z6efPK0+oLZlZxHYA08N\n" +
            "5BEADa3rh9XZXzz7p//jX/7lX56DaPVo5VZd61oQ1H7fPP/q+eb19td//aunJ09Za+l2pLbpQDFA\n" +
            "Elpfo1pWn//iDOb55eWL+kFZ/OLUug+dTAEYaFXrktgIKooCoKIkbdTFek1AWaA1ANtUAqgfrZ48\n" +
            "rstF2Q1v50GIMT8im3wu7H0GepF2IfaGmUk447lcFjafYXulTj5aFUVBQkq0ABzsL5juexVDUHlf\n" +
            "wtBWNRe/e77+dtM0W7UDDOp6efbZ2cnP6nIhIQtoF8XIpiWN6ngJSNYaVOLIxVZo0HbbbNbn5/+2\n" +
            "Xl+uN1cgoChQP6qfrFark/rko5Pqw6X+Xkkr0cimV5MkiO20eYiVQbPlzasNLejpyUkLPP/XL9ev\n" +
            "FYwiUZCActm+kwgCpyJZ96FxYjE+OHCKBPK6wD69OEuTO3zkfg1O9P4/k/YGQk73ZPi25Eokq67B\n" +
            "hqul/P+pe/8fOZLsTuzDZiTnxW7VboSu+pQpNfeYd6SO1RhKU31LQd0HHjANjw7bgzlDHMjG3QA6\n" +
            "2PKXH/yr/wT/6l8O1g/3w+jsO+zad2tyIQ2mCd3ARdzS6l6Yi66FOGYRNwSSPjaUZbGhDKjLE0+s\n" +
            "UNk/RGRW1rf+QnJm5YcCWZ0VGREZGS/e9/c2b7aLoek9yqxhprqSP6y+FOAGcsP9R9nG9bZSZI8Z\n" +
            "1vp09FIiWUtUUxtj2FvOHBBRqEVJZAZF9/4eD3nnt3d23tuWTQlXSOkxzpYIAHj+ogkuuH0jbT9p\n" +
            "dz/rxfsH6VoiiUqPYJA/iRnr12lrM6WGLV4MtPb6sCpW3zuDceBUI0ittt/dlhH+h3929+4nu1rR\n" +
            "5uaGluWuisgLJsUQAJKWJqXZIS9496cH/WfMAppU8I8dsm7Qh5vtdC0hUTogihorMbvbyt0bZNqQ\n" +
            "NaRm/WY48glOdEu116h9bX3jZkcp3f2su/tJF1wQID23HNVvSayFbJK1/Lifdz/r9noH/acGRERK\n" +
            "CTAjH5iD3u72ZrJ1q9N5e0NSlc9DMqCaMMdBcW2PAYHBwOztH3Tv7fYPmRoqIdW5ThDIj7j3JO8/\n" +
            "y5KH8c73eOvdTThKKCQpDeyEC5XIpZBSkHE5OdNO1PbNdWrF2pk79/aygSFhUBUUqgh+kAgobAMX\n" +
            "solJpWVjjhyeC86J+W/Ww/fnqPCryckz4CD98S/0+tvrEFI3Dg56fT7y3Glo47UAFtANVRwN9vYP\n" +
            "Nm6km+90AAVBMgpBuESKlGK2cIyI4AjeqtfQALLDLM/62+92tm5uAD4bV80kNqXW9iU9QUpvv7tF\n" +
            "xBs3EhCsA0WgiLygLpXeener3dmI4yR/MQBAgviYEREg4ezUGQcAsCOWkjrvbGzd6nXv9x9/3tvs\n" +
            "tNGsWFyCQ/GiYAYJ6FUFgLQ2ed69v+8XoWCGIB8E3V5Tnc46fAyiq2IHMHmosO7saxFQRUZcud0r\n" +
            "ltVbyATgkCi18972zq02NWTSiknIvJ32H5FuyinddRnOaB0gqMjN7mfdvf2D/rNB0oh33ttMr6RJ\n" +
            "kvgsJtkX2d7D/d37/SzPihe8tbmhlYJjkgpl8CVJ7R0f2fCnn3zafdCTgm5/r7O1uZW0tDdX5Ed5\n" +
            "luX9J72DXvbxv/p+Psjff2/LtDyyBq0QHIikf18AiiGz40Rp1VBE2Ly1VQyx+1k3NyWaz3n6MKpS\n" +
            "KwAzEVRTUaN07P//Ibw55D8fA1Nb1omwQNMtNATihlJvtxNF6Rrdud/PB8YMQYIR/OqIHXvbRjYw\n" +
            "n94/sI423m4nTeV1vxbEgCSZH2XGFFqFu+BATcqfm34vA9H2rY0kJgAkwcdlcv4pLJUYwTqWmnDM\n" +
            "7etJ++pHuql4VHLLI65ifttpm0dlNFFEZmhCDw7wnqBTK8AA7DET4cMPdkggXUtkQ9ddcazF4Ii9\n" +
            "paowDNDgRdF9sNd7lBUOoBCJhCG3r6idWxttbxuJgIgCmxo057UVrlWnrJdOllQ2q3leQSBeSzqd\n" +
            "jY0rKqjrHK+vqf7lxBY5uE2aatvfBz5oHtrdz/bufNLVpG6/t711azNdS7XSfiAIFO+knRvJ7oPu\n" +
            "waP+3R91Gdi5tS21Cm5Oo4CCiEhGtP9gv/cwW7+W3v5gZ+PttlegWjBGSNK0/Xa60UnTq/3dT7rd\n" +
            "z7pSYOdWmrTUlMk7mmxRU+RcWLpKJIChWV9Tg3ba76niaOAfYQqba97BCPXjg6GeIM9B+t+8YT/A\n" +
            "qzn8nxP5l8n85+tmyXJVzDzAPqRBwDIToX01TVrKUrK/v3fwswG7MqGiAwRZBpFi5v2H/UFuBs/z\n" +
            "djtRimRDx0rphozX4vwoK5hjeOkdGIId54dF9nyQJjKJFUmC8+ZjUFQ5ApX2JO/j1GR7zBKsm4rB\n" +
            "xbGRgkiCwdZxSFbj49EcQ0A1lWEua5ISVwm/pkGWLpDta6lSH0FYagYG2ALSgZnzI+P1F9QgZuw/\n" +
            "3Pv+j7qFI2qU6beHnCjavrW1vdnRDYQwBM+meiyajkH2m1sKr9WkQCUrcicAb/US3tWYZENpFSLw\n" +
            "PP6nsdZKDQYZc8GjBACiqZ3Qf9o/6O2tt5Pbv3V743obEaxl63IpQREYrAn0jtZXPlL3druf7XUf\n" +
            "9BKddN7phNElKCK2bB2yw2zvJwfpZX37g52NmxsY2aLIpNba5/ZzDIc41jvvbSvC3U/2uvf32teU\n" +
            "ilPp1SsC1lmMQsIoOBSGC2ZqaAgtRU6O45ZKkkQ+G5S5JEK9oMk2KFWSJMpsKMzsLINO063N7fM3\n" +
            "hP+vqZh/EzL/K2O+v3GCafU2XB2u8LJ6S27f2gJQmG72zIQEVFQleyIiFENjn2X5UZE8oFhJvara\n" +
            "V1K9qgeDQf6i4GEBl8CBmsqrD3RDt9M4aSdKSXbGB9ippjLHppTPPQvgv0t2ObtCa+1Hlz6ZpAXp\n" +
            "Wp6PKimAhDk21lkpNTx6LcJ8L00APpiXE6UQKbbsCbUF2xGYUQwLC0AQM3Y/293rPc4ODbVIkuJh\n" +
            "6KHdaW9vdrTSgMf8gPAUlclqysyCkERgAlEpLKCMs/C56DzBtEHDQhbGTjJ+BQOB92/LB8YMWSuG\n" +
            "oGCMHIEjApB90Wfm93e2N260vUglBVsHWMtSEixLSLCS7e33dpixt7/XfbCnWkl6JfbGWh7BAsbw\n" +
            "7ifdPDcf/dZ2eiVlawGW5DUagbRLKTECBDZubgyO+NN7u/0v+un1dTQIgmQEONho4hQQhPYmMawW\n" +
            "YOZE6TROiPrF0PsgE9fFn7q5qtq9o0XocBJQxUm9Jv6/EXvca/exzL0BmIiCdSvAzFE60yDcxtXt\n" +
            "UsCW4kC6Su9vtrXj3c+6j58ywFKYwnvW+ADARmwAY7h/ZAhMIiPqJ4rMkInQzwZ+N5CDdSbRCg47\n" +
            "H2xRQ0sqsXc2o1NwO4EAYDCCFhpMGLEvxRUasMfV8F4Dg+3AYClk+cKXb5QRAIItM+GPykXw2ytC\n" +
            "4Yw5BgOG0d3vA961URHDMsgx2LSvxDu3NtIrCYQPCZ42NXlvXEk+xbh3TZ08pPeWL3dkKOXjUwn6\n" +
            "9GEOPDQ8ZM9lBNdAhyRO+k/7xZFJr6ThActD0Awe9x/tJYluX00wCmeHDuKxxAhl4LLUYGphe7Od\n" +
            "f3GQfZGxySRUELIk6Yi6vW6W9bc6nU4nlQ0ut5Dn+2Wg0kFHw0lLdW4kBw/p4GG+2cmTt1OvxpEU\n" +
            "5uY1sgVbCKgWgAEHZCc0Sm8f+NUoTY+lS2/wC2JDRBJINCliqlISocbYz+P2uZKjTMM8D78w2vd0\n" +
            "mD68zsv2L7xYJrqrnHAW3njCFOv2/7khZGBByVqTJpre3YKAenDQezoohpV8jor5ooYEB3MjEwy4\n" +
            "cKwZ2VHBDGgCQR6DLYOgaZLNKsBoaWp6KpN50CSrB9VD5Wa4+kn75Y8emo3IG95QqhtLlwcLAWZb\n" +
            "DA0DRKoMQQ0pd6VAwWbjenr7e9tb3+1oSWwNW/hwo9o867MgjDjYqzyI0jpbP6bD0cxWkOc4ihc5\n" +
            "rickiX2pjwYllxOltHmRY9gGTQxaJAjOpJeT+EqilEY0ieaeX1svSLevpu+/t50bk6zFfj6+GmJx\n" +
            "zGmafPRPbqdXEtkoA3X88VqWSGEAI6IokJYkjtvX0n4/N0OGI0lhNfiYIbw6hMGsvfdxmc7AHOb5\n" +
            "YVYMmQTJ4H5EE49yYGL/F8SONUE1SE8ex2+/SgSoEfkT4ZXT8r6W5l8A56rVtxRqttDSgoXKNDIZ\n" +
            "QZRt6mPWSrKgRnxOmJdSaufWVnq5nT7s7e3vDY7Y18vQKoSCQ0CRkqRDV0PWIAJbw3YIWO/9TjwM\n" +
            "EYF/PaBGFiIEWcN7mzrkg4E5ykmAMVAN0qTYcGGMZ0u2v5t+eHtn81ZHC7AzkPWCf9NQKeRDVEKp\n" +
            "CwQmKmsB+MCYEq8IYHBhiv7TftZW6eXYz09pqRrMQy+SsJZq8vadUa1k53vbSiW65Kp4Kqnn1LMT\n" +
            "iJrYfm/LMmQZbuAVqBLYuN4hrXlYlHGaC56rzPDFACctvXVrK71aJLGyMFVJVSsIDClgGIY5jtOk\n" +
            "lejVpHhR8JAP+lk/y+GCUb9WjzwMOvlW/qRJhiNPTDdYUuz4ZDghZd5yfd5ZKn3XQEx9f+29P6H5\n" +
            "VLuyDHv9zpjfAbVDYTmPIAVZC3ZMDdV5J07iJL3a7v7ve9nzPDfGGsNlHmU4FFx4fkwGHxWYvBgc\n" +
            "5klLBc0NMUnwAtPbIlgUCPimYcq25KUPGVFxzP1Hj/MjTtYUtYiZ2Rhf8yttqSROfvc//6h9NdXC\n" +
            "FsdGClAT7Bh2huyXMGvC9EVTasaqyeub6LEIMEOz9/BxSha3tlVLkyAaghqUXk3NUW6GVvpo/DLA\n" +
            "WTdJN9sg4Dg4wNolpfu8wzCNAJBugkdhDkH5SoQIpihOYpt99Z7KDidp4+02biofHFmmIWUpCA2y\n" +
            "jHxQFHmh2okZMrucQP1n2d5+Lx+EKqxFjZEvl8V3ogJlcyABUqruSFe1to4953IWOBfBn6P2r1Xs\n" +
            "a2kOv/NrFGbtogAm7ECdw5xqOcdTASfUV5UCsvS7iFu0vdlpp0l2mO/vH+w97HkvVx6aIngBkG5U\n" +
            "Oh5kT83jXta+2taq2utntNDSxH3lK4IJNS7tCwAAC/gcvgC2brQ3b6X9fr/fz7hF7Wvp1s2N9vV2\n" +
            "skpAwY6l9GkLYY8LKXToazQ9SummSjWpnwCe4vnZvzKLMlePIBKcHQ7uPACpwcZmqpTMj8GOSaX5\n" +
            "06w4MkkrqRNJgHjEvmwJCSDCgvTHHgT8KVzqFGmieRHgqPTqx8ksNGNEIbRxxBUXAFlaMqqhHecv\n" +
            "DIRMr7SJYhDyI957lO0/yYphufCVADt74nBljSKC0ppmzk1R0eIlW2X+EV6Ve3/9ir1viOsN76Yu\n" +
            "5Cw8C06CMyGWL40iAKH8oZeuxUlLJUqla4llww75IB+8KPIjUwyZma13jFNEAlmeFSZPWusEww48\n" +
            "4gW1AGtWsfBDxPW/3jTUBJ9ZIPPCHOzvDZ5xoqjzzsbWb6TrV5JikyGQtBK1qnRQ4zPAFOzYVi61\n" +
            "PVcBKgwqUwZPZhB4/JBeCahnB6SG4iEPBoO79z7NBkU/RHwGAAAgAElEQVT7Rjtd01opqRVAZsgW\n" +
            "kN52gCIkLBsBjtnz8SP2xcsXg7ekAvD5/EAkQqA+WyYR8n/O64bLhSMAXP1a05tWzxfaQLJjYwqA\n" +
            "jCn2ftbDiHs/y/YeHhSGifxAgeefiBI17KqKpskG6Yaae6Lzb5XKawAnHQQLdX6vAFIQwP7ecxTq\n" +
            "XAx18dJNP8myxhWT70UGD246f+PSWZGcHC6QAl6K09eTdC14jxgu8iPTf54VAzbDIs+YCBs3OoqQ\n" +
            "Pc0GzwdpK0HLe24XS+IfKkd3ggCNynC0rxRqykL2+v8I2bNsf79vHXY2O1udto5YX07Sy55RIjiw\n" +
            "NRTV7nUA5CR7/Az4BxkFdlo3JRFhyNVuKB+89qQC4KD5J4JlOng6eHy4m+z30jRuX02NMfmLIs/z\n" +
            "9jDVSgVMi8DOO9XUU5guoQcCvq450aS8GqKS7V+WjbNaqylP5JCix/sRTAyT8JFXsM6yQzHkgeHu\n" +
            "fi8/KnjIBbMZslIKAjzkSS4gxycgMQnyOqOJcDQXCHxWOM0QMJ+0r3ZlaUGcJV1NxvpK9V11msaz\n" +
            "1xdI/qdBeR77eGwE1Uz4STdCK40kXkva1xNmsIM1FkImKmY2xgwePz1ot2MtEz42YQalL11lFQdq\n" +
            "dbu/ZhCl4c2Bh9x/lGWHZv2K2n53S6lQd4gB2GAQLV3zeWqRA3mfBQbDAk2Cnb7oapbXCdQuVqe2\n" +
            "CJiWDfLsWdZ71FOkSBhvSQkOC0ICZZqdunJ+CUzmPeMHIWYvLu5ChDZ2yLKhJjlC7dRrBWAZDGSH\n" +
            "ef+LvDAFGxRDA5/+hMr0RwBANJPUtMQSxkRlqVY1ea3IdBTgKVj4Fbj3nTLQchR/I8h/qs58SeHN\n" +
            "hfeWitPyzkX3LGA3ZnYGyyahCYCQxN6qXBhWirJHWb/fS9Z0EEHnXIzKI+ArYvLnoeS9PWPs2Dvk\n" +
            "SaLBoXn8KAOw/e5G5512tVCE2YpAM11NfatjVJlxzAvSPt+uX0ApagfCtIOA5zK85w8DABGRL+Nl\n" +
            "HRRJZmOOuBjapKX8oWmOLTV1rZfSCLqk0gmVckp92Rd9n94OweAX/DIhSDW1Oc73frIHga3vbjBm\n" +
            "ja/GmH6/nx1mPpmfDBoN5X0ZEBiNkq7UZyhqkoUDAN2gCdc8r6VemEz9VU39JybznZw25xUEviLK\n" +
            "X3/y+Qc+K16dcufUas6MOMdowFADOqHiPncf7KXtNE1T60jXeGbv1ftzoPg1FQMDiCAjssd4/CjL\n" +
            "DovN76a3P9jRq5KL4gRsPwsQAAkIwEJGpBSphiQRwvOnlds1d6y6pC2C9hReDnfITQHmLM8HR2b9\n" +
            "akoRAeb89O3VV50iYjCBrGPmIh/k3//BXSDuvL0RSi+U9J+B/Kh43M9yXw+KgvqzjtKLrXR1AuPg\n" +
            "c0BTLZz366PnZ4eFmD0tU6+8bndnbOwqXrQU+KvPEliyHaa3e1XpYb63+vcIiCAl0rUkuayyp6Z7\n" +
            "r1vkBYDCAiPwCDzy/D9RRAs98L8i4FFZPjyaMEEkkB1mdz/5VBI+ur2TXkvBRcnb1z/VyUULPpPK\n" +
            "duWiRV6RjqAxaWifP8vDJCmVq/cZIHjFhza+2CGY2RjDjOLI5HlumYtjAzGh5F8HeNNgBEngEauG\n" +
            "3rq1ufPeesh3VH+VDsw8GOSBpXclMa8cHGYKDdUM+NUV9rcQQpKyk73Xfq4gBUmxyGxXTvjMCH2C\n" +
            "995COHlFlqhtK1hM8yuFiqtMhuWvExzwwjBRWdvP23ghYC3iteT2P/7oB/b7d+71Vau//b0dAiBC\n" +
            "KvhqH0z46q8eCIAI4cAhg3BE+Qve3+/lR2b71lZ6NTUvcsCQrFfsqevkypeyzKtn9qK/l4lIKQ2i\n" +
            "kGSsSlbrXSoFamr/2rhepyuYFMERHIM5H/LB51l6LUuvJNIpHBtqvuqKnBOCM3glrDXod3/no/IK\n" +
            "B1Vi5E9DLl5wMTRE3rUBAILgE8yfDAEq4x3rxcUmy10qnqSgiXEEqBRvlanvtUzwbwLquj0pAJCd\n" +
            "RqxzUfPXOM5rKvopTdLZpkC1LzN67EWyIAELIthlBEhK15Lt39zOTffuj3Yh9Pa7m1zXQrtSTbVQ\n" +
            "Ov2KToTAKrMUBElFYboP9j69121fTbff29RawTI1l3m3gaaevvaOFp4U1YgjEKHMaIyJKl4Ey8t8\n" +
            "/udJkh9fIMkFozcEzBC9fj+9opNYk0CtYs9XDhQRZDANBOMigilzvnEp7YffJtbBaavTYrSfHnby\n" +
            "dcEG/torcJ4GdsaIA+A8yH8uzD/5xZ/PFnpSu7oauY7tI6pqhRBMVZ7Ba0Ta72zcHiUf/4uP736y\n" +
            "R6S2bpL2FTJR+pYtCDQ8z4zPC6My7XcEa033/sHde3tJnHz4j3baV9YBS01/5NkFaqQ6LPCbXqQ5\n" +
            "HYFkqddshPj8qZ7F/MERzmtGGUld3sIACShFueHu/kESx+RsEqf0dSE/4J32Q/wvNYmP637E4Yt1\n" +
            "4CHng9yyT0MMCKpyk3H5qKH1jH/0PJYExvPnT97PCoue5czIPxu3fy5EWESe69M6swgwu6Vm1fXB\n" +
            "paz+Zx2kgBWkCFud1Ay2uvcPup/t6manfX1d+0g4n8eq3mFlX/Rm/9M4gon5cL5NLYte7TEIkfRD\n" +
            "F8dmb/9g914XjJ1//H7nnVSSxcgfXnKe61kEZ0K5oGUAtJbUIBiuPP8qzC9NifV+Zz3e/K8hz59D\n" +
            "/5m5e28/H/L776XtRjmQqFRuXBX2qd0NoCpkXMIsh7WYtam6soBEqLNIDoGwO+PXzDogUsahf1j0\n" +
            "n+cTydE7Akwv6Qx7j5k/Rany5HC7dSwFJj72XzXBX9b/Mgw6cT5fg7Z/ycWZw/U0C8Gk2cwBtKhg\n" +
            "zsx2IXg1PmMECYaDbGDnvQ5c3t3v37nL7/8j2flux5tzSIaCOV4CRChTYcEMnSyYULVTfRCBxxkf\n" +
            "pTeZx2Q+U54uI7YgjCwiQiT7P+394IddOL79wc7WzVQSvMGZwIgURqBo3k9mYWbO6SUR5Z+2pPAW\n" +
            "ECwJaQtJQw6c8cE8pSTM08SfSlmACRMFYeUOxCBf44wZ3Z9lhVAyGbS/G6NysxmFZWHwAtFpif3v\n" +
            "7CBLx2FJxCOC8P45BUaaIiocAMqNuXOvmw3MRE3vQIB0sGKRZc7DjJmjKtAy8SCqi0cSIFkZThZo\n" +
            "2r4ybqhykSzhlAJ5AICL/+1H2+cYY2IcqOg1lwqTmc/8o/PUT2NgPNXtFNO6ZPzznFWzbZ1zWAGB\n" +
            "HBwA3dDy27JxST7699nzPBPOJb+cNKlx/KVxTkQrzjkhhEAkhi+dc46+veoWbtMxcNF/YYydfy43\n" +
            "FgLO+YeNgIsOAC4CF+HGcNUtbwl3QeCiwAi9P3n48e//wYs/Pfrow9/c+Y9/U1yiZgRchIADCGMA\n" +
            "jIvzz+V8t6FD0AIjyrh8cc4hcmEHrgAReDh89PRF9mdHWCEHOAeMwc6JFdCKE2PAAWPBDhgLjB0E\n" +
            "sAJagVsBVoCxAATGwNgBwsE55/hLzv/v/Nq3I/GNZlM3eIxj56IVQZFwfn0uUpjhKOyB2Vc1nvm4\n" +
            "8gvVr7vq+0WHcbXxGGCsOLESwYEhsKIGf2bu/lH33/7RAwHhAAfhatSHq13tyg5XwmMKQWJFiBUh\n" +
            "xk6swLDD2Ikxr7boN//+jWt/J8HYRROkiDAWqJineUvaeG5fixoWnAXO3Nidof05Kf+UnXCeCp0R\n" +
            "zifzv2GoErlFaF9pk1Kk4rs/3L37o11m3rq1lbQUIq81CA8YeI3RuWZcLU6N5o+ACNT0FiaG8yWA\n" +
            "iY95b3/vBz/czQ/NR7+9vfPBjpS0xIVhISxkqaYXufSZrR/LBFBDKaVJUOFYgsyUx5RfAZ++f+qd\n" +
            "LaBfogyMF2Dm/uf9/36Q3f7t7a1bW4pIEiFCYY2sE9jFBH+Or5m9OBFETmMViQUsA4S9h3u7P+py\n" +
            "nW082TLiStPSVD3isl+BuJVoraXPN1+nuOe1/J1XTDg7vp6h59dk+18Hjc++uU+DM3KSk2QMQUcN\n" +
            "IFlNtjcTMN/9Uffjf7WbH+Yf/taHKlZSknWwQwaRlIoiLo5ZN5f0vyglPqon9DiPUNW7OGYAMiIL\n" +
            "ICLzwtz55NO7P9pXDfw3/+XOh//JbRKyeJHrJs0bLJbAtEjsZn6aMQTSBG1GIIEQ+BhwHD6oLvTp\n" +
            "5pBquYIm2CxFcEx+fGiKH3bNENvf245JwxaQcSDLZTYBf9/061/OGNefa2YOUx0CsNbBOg2hQHL/\n" +
            "Z4+7D3p2kjq9PtLyDTytAZka2kE2iIisgxZyiSJm0YNU8tcZzAFvLtx2KbxST9N+QmeG+SX+ykSg\n" +
            "xdnywgSozOUGACPIBm29uy21/vQP7+x+1tt/1Ot02tu3ttMrqW4o6xgjZklSh4pXEwhuS5UqdIYu\n" +
            "1kT+UVk/s/ppBHOU95/0P73f23+QpVfod3/n9vZ72wD4uJBNhbp4PF9g9xwwq/YP6jcLCICkUooE\n" +
            "MYz0B4c/CoLmLCwae+v3dL8EcA03qoALAEQkBWeH5s69rgW2b22ksZIEexzWTYpa53OzpckI5Zcw\n" +
            "Oi80ItQuWVQVpUlZh+797t1PPj14MmBgkuwUC1ROU3gullz3mgtXqjCZmeqUn6buPcuh8Ao+QnPt\n" +
            "rVsi4J9WtOcrUvidNtqECv3cgEfsnfl4xJqw/RudNI4//Wx3v5f19vv9R1nn5tbWzU6ylrAgMBNp\n" +
            "uZBQ1CO6qxgSEBykDOoxO2J2wAv/FxljDh493tvf73+escP2e+3bH2y3r7et9ZW5iQRXCvma08Ey\n" +
            "4/kpmtTaI5etpffeJSl8bB9T1d7VfV1r96JMRl7Vxl00lQoKJhbcHxjzSTcb5Fs319O1NGkRAFX9\n" +
            "g5lA/VqXXptYFUFcNKXpe4LlwWM+OwyG2Nvfv/OjT3tPB1Xq/lnwKt6qk5OxwZXWAQc1KcwtJ0d+\n" +
            "fYgzovTyZosp/Bu1JoQRTk4kZst1fw2r5jTaLHoT52IDpjjdxeruefvQIouRj9iVxJbTK8nv/me/\n" +
            "u30r2+/t7T3M9u/v7e8fpNfS9tW0fS1NYjaCFVX6Xm/iJglYV4V2sgXBZ6wSVPjsoszFsDDGFC8K\n" +
            "Yzg/yvv9fvbEkKL0Srr9bnv7ve1Eq8KyBNAsvc2rhULpBSAmCvaToORo5hakDuEJCKQaSpKGMCUq\n" +
            "0oIdVueBz0IvhI97JzjkR2b33n6/39+4sbHZSZOWolgH3Hfw3kS+eGlFQ0N1HchQ/7vaPCFRtx9i\n" +
            "6siztYvGmPyo6D7c637WzQYGABHphsqPDDVq/rwAfN5EQQtl+9oxOsWlkw/mJ0UCCFqF82FG5Tq5\n" +
            "HN2snKbbEwydv2EhhXennxRi6slPhZOdTM4FNbWTh6/SKWSBhSzk2/XbfcRV9oj1t9vp1aRzM+/1\n" +
            "+gcPDw56/YOHPd2KldLJKqmGZgcqEwTJBuIklaJKB8jeE8YWYEb2NAOQm8K8ML5WPDlAqaSlt99t\n" +
            "d95ZX2+34zWlm8yOfUGBBes7ISavtkITXnTCTrsycnkE3dKqQsXlVhYCJjkX5gwOi26hYCh1gOPs\n" +
            "mcmedff2qX013eq02+00aWmllPTejQRffmPeY2Lifyk4mNYEKJIMWFtrKwiQzMgP8+7+Qe9hr3+Y\n" +
            "+5Lf1CAGeMi0qLQWA3TqwtbPYsdEiFu6DC2VoU5jRc9Cwq+zdfgKsMRX/yQ7X0m2Z/QIwiPdiaX/\n" +
            "3oiO4ZRT47z7ekF3p4jES0aYdhDyfUtSGzdUEiedTjvLsvwwz54bM+T+F7kd9o1hIhgH7xKvWr5W\n" +
            "b6jrGNRIPuM1QykyzGkcp1fSpKl1S6XXkngtTZSihpSCEPFEVMYS35LlD70UZlZj+cslokQrCFjH\n" +
            "ILWspd/fi2cwL2jMdkIQTIKKIfZ+0u8/yZK1pH0tbl9NkyRJr2jWqqwpwHAWDrqpUcu9NclTGUnP\n" +
            "VthRpWchdmDD2eGg33988Kjf/zzLfUJHEfLzTjSXIfNHKU04wC1PGzfH5vhcEklMyeWUSAJeQJML\n" +
            "G8+DLal9HaGm8naKSiFyDlZivqk8wc5fA1G9p5kJ4bVwfpEmf3lvZ8f8N2wknDMKBEUOYEecrKpk\n" +
            "VW3caBfHbIyxQzsYmqJgY4riyPR6BxaUJon3YKlS0JAAIs8TymQtJnD2LN/a3GpfTamhSUASEBFV\n" +
            "VE5YuLrn4rKHO++jn2YIq9oRKSU9/aLG8sy/J8Nce6p/rfgFxyAqmIvPs8dPMt3oJy2KU51eSdbT\n" +
            "OL2axr6Yl0/IOSr78FLPKFQEClQqUhjxwHA+yLMvsv6TLMsG2aAohgaOiFRwVSoxPExgbpKMacJ+\n" +
            "srrOMQmkaZqupedYnLPDGxXpAwigOgumfWm+FoXfiYO8Ordfzw8xl7Jiqs0pMDsFQqgn5cNFtYCO\n" +
            "lYVKoxQARjJ7+piHRbqWbt3aUg0KFWBgfSkcEp4LJd2Un+5+Whxn1NhQMckIBOsJGjsGh91Wqw5W\n" +
            "5Qs861TL+b46kEASx4rIGibhA9qIRIjbAU5Vgy2fqivnVhLb4O0rCA0CkLPJngJPB0T9hEi11Ma1\n" +
            "JL2adtqpVjrYDgEOpVPIMnjIzLYwZlAU+eHg4ItscFhYZl/DGaTQIHjvfSKfyimIY9VCeY3gCc9S\n" +
            "OUqIOfWHAwjpWuJzwMqJUrZW2y+4hC7ofiE1XZaW+xVI71RCryW3y6rl4tTdy87+V7btncc0uNSN\n" +
            "ZB7m9Xzz5v1pMRIzCeEWHA2VIjAkq6lkOenL7I5syAflWCU6vZwAsCOf5EaVzWCtV8NQ7/N+9ozh\n" +
            "ICNYWxSOSZCvQocg0Nbga8wjUIEUSC8nupXkg6wU+5e6by2zN0zB3P6pjKxJnBSmMEMDwaoBEkQi\n" +
            "FDfMh1wMB2YwwP0eHCkF3QiLX5Sk2wwRVKhBugYJoEEgqAr9HBC6rU0mnGU8OYNmZluH8nRYYJp2\n" +
            "SJRKr6RKQDqvhqj5AoXddU56VluuV8zDKwDnMd8CsJCzgkCtW1tjbcTsMSMAfzzMSQE4x2m0yKTv\n" +
            "TtcXnkrCFvKyJ/gPzP5klxjMJyvCAHgECgW54MvCeBUxRcweYY9yzjN5nNoXWakuK4n2iAFYQYhQ\n" +
            "HOfMRgqoBmNktA5DMBiT0vESqJXxm9i05h7r1XnCU5SqSZzEiXr8ZMZsVpsSJrMilKs0/2s1z4k1\n" +
            "hycJsID+s5yIqJFA2MntDUkAuEiU3uq0k1jZocmyvjkqeMi+QqF/BUppcpQf5SQgiXy8Ojtm9ipY\n" +
            "ZscgkGDjgkkySDHM7KBqYYjVVLl0bZh+BA7/Tr8FIsSxTpKg5Zkr7rZkQU6GmrLgdZVrNSpb4f+s\n" +
            "8F+b2MTUJ88y6RMLJ5yOvKexAKcemSc1ODU+ZFGDiR7oxP7DDh5xxYtCQGmllUJEZXEIL6wyAVIq\n" +
            "OJghF8eFT7tpJ71Xg8hq0SpaRC5QsInhfYFzzcwT0GzFyGUcxCL7nw2WMOKqqEb5gia8csUOTBw0\n" +
            "Zpeq9jfTzCQDLiFW/unsJEjUh1Gy5SHrBtKr6c67W0SAA7M1Q2MdpFJe01YYdO/3du/tFkeFJUgi\n" +
            "CxDKvKa+kAaBHZhZlXQYjn1dEJ6YKqcZwOnp8+QLE6hM7BF4gUTHqqGrlfEWzXOLrjOahVc+1h0s\n" +
            "LABZ1/o5aytLxImnyZmOmq9B23+W1qcs8alpXk8yek+bZ+ppAmoeu3bks9MjezEAETXKwhiRV0px\n" +
            "WWMTRWG0VkTExigVE8UAMCoNbABGXq+uWVjI+s47q6Lu/M1mgEpDD7PgcgMb2VD50JBQEBrCAmBn\n" +
            "AKhZjWOZDyNYN+d6n6GZ8JYlJlEzLTlAMAtphlAKhcvzwZ5hlTY0BDTBk1j2ZcQjb0/J2eVoACDr\n" +
            "cc+XGAFCwlADgOIpIZyoMfkOVLueILx305x2QwBecBDMIb8LgVk7tMloUSQTFQbVMoJMnnXpSxGL\n" +
            "/jwD8i+gzdN3ScAK6TnK2Q6XjCKmuq6uLRT4z3jxK4M352MwAwvf07x2nWVEdsQFGx5aRASiKUU0\n" +
            "qjJ7IJB1MKbgyrojVbVNAwjAWYBrJOgsMnVdGn01tOf6QBQRMbRKEtUrGAUbEopITpTkNQ3ZrHdt\n" +
            "uQGodnrSCaRCBK+eqdl4xygwD2GGxg7ZKq5CgKpufalyHppJiYGZ5SrV2ks1FlV/E9cJTPXgpvHE\n" +
            "eXXFhE2IY0pitayO6+vDK6n6JGAtLCAD/ns4A2LOFe2YH3jplXPo5v7awmmvcfYIkCDD7Eu4h6Vb\n" +
            "VKRdliXxKOS9kXDgUS2TdKlNmJ7Aifv2BIgWqT8XNpsMFK4wAEHp5TRdi4snA1/xiocWJEuDMZ0w\n" +
            "sWoTnDLvaSPTTNEBRRqVfo6W7n7LzM7WiywFhmKiKJmd2KnAoe3c+etNFZX+woEatH4jSdNUN8qs\n" +
            "5G+E+J2rh9POhUrVfxbd4RkOmRmuZgrzvypYKnv/PPThk+mMyEfF8pCXR01QMGgLQEgS4CEs25Le\n" +
            "EcC1+j/zD3oW/J8jengNS8EIWlAcJ0pJOEgCKMj2EyHZLa+0JUo8qf4spzgPZSmhUvkhUN3JDNVA\n" +
            "3Ip1Q1tAlsq22nN6zQl7bbT1V0Qlw3OdofCRSHMwF0vnm5UywkTfKRilKsFrDQBolbSvt9O1FKKG\n" +
            "FOeViE/EybPS/FonC/15zmg1mEs44BbNz81wSvi5EfzRos9XC1x9rANA7KxhDuWAF5H9Cnx2d2N4\n" +
            "kBuv1S9FegJCQvFzIO1o2h+xulgb8Mx91e6JCJFMWmqr00laVAyZhwbOe9p5kwQFzwWB2U9t4PrY\n" +
            "E4JcbxmCGnnmYmlBwPqVeL29rhpK1juLiCJCBBn5Go0AAMfsmOd3YZjnknVYuLEF2IXPZJ+78jWV\n" +
            "V0ggjVV6OaGGRJkY1zq2/CZydb6eTk1CytK8ZwEL/+JO9/FbPOwpskeF/1+Li9DPDxYgtrXGGmOP\n" +
            "i1ilfh8spdTOECFNk+xJ//GTfrvdTloEBxbLqeiCcc+Oz9WxcoINFJVWEqi8pBhg3aDtW1uG+dPP\n" +
            "utlhGcDnb6gmXDPoLD3zXIXki36q/vVdBcrPcFCE9o2N9tW2rGVhn4CPRxRSkQ5POGPWEoRS4Gd4\n" +
            "VuUEe8SJMK1U8wcZCcSrWvky5F+jnuuscF77Yu2mxXC6+uFrX4XFzOTXOQEHM2RmyAapisLMOdIT\n" +
            "wCPohkqvtFn0+/2s2DSqEU+KWcwYe06Cad3KaLlT05lEUFp4uBCQtNTtD27Hl9Pu/b3eo35+VDYT\n" +
            "zCXaL15qMYswU2HOmPqJq368kc8xMxNYEtK1RGmyxyyptC8KAL7sH1sHItJUmvYE0aQqyQyUwTaz\n" +
            "85zz2POnjy8uWFL4EnjSRgCCZIMklcdN9erF69Jt4FXwaLY6Z+WJA1hvQj6LzD8VV+D7dcAZo3dP\n" +
            "kv9r7NBkK5Sm15N6OwkWb77XyLFfK/kQovompl2peMQ+xQ1FYEA3df4iL17kAKdpgkYp29eZed/t\n" +
            "KKem5AIbnfZHv7Wz+0m3e7/7odrR1xIeGR6BGlS8yCEgI0kjqvL5ep//wE1UCWq8gXDe83c6cdCc\n" +
            "EX6u/cTOX7YZBUrp9V5a0/ZvpOvXk+wwy57n+aDo9/vZgOHYMhsuY2CCnpIYgACGPFsdyE1WhF3t\n" +
            "FgGUNX+lgHVMgiAYzKpB7TTGiCVNfKv8DCkCg2QEOFINpUAGrOGdfAgOttxsXnkJB4jSfOotKUQE\n" +
            "mCEH3PYugF77IAjOsqt6IBIAmAhmYAAoggTaV5PtTlu1dB1Z5Fem9l8K3hHwxCYz7r2TyitzsKBE\n" +
            "9zktDV/7879ZCPhAGHHIM0NUJvBmAGiGjWKPi8LlAHo/6yerSXolhVsafEWR9GakpJVsbcrsMN9/\n" +
            "uCfJbrttFUvdIGZIoUkSBHjE9pghICVBEjvGiFGvFHqyI3NQH57L/lcdefCnXsnYMyRiQarV6bzT\n" +
            "YeZ8UGQDkz/P+k/7WVbkBmbIFLRxQR8WpAIx6ZkdQl4wIo3K+RRgJiIN8tW4eWhIkCYwsHEtISH9\n" +
            "c/AIcLNZtwCw4yRJtKLBoaEGLEOTLIYF3LQ6AKUNT0zpIpSP8OPacpXKvJrVgINf0JBVS4ENM9JY\n" +
            "bW120isp/bxqN1cwTdInwY6v1Nk5s/fOzOMkzBdYcT4L6mJTzDycoW6gO/NHXDzTQ7iLPnkuU0Ty\n" +
            "m60LEHCEFeG80SkijHDMLorgVqQY04//+Mf7f7y/dWuj09nQTXJ/6TCGuDg7OMAYwzkBIfQvqIaM\n" +
            "jOGH/+ej/PCZc9yUJC7JaAx3URxbRJFoflNduCisdTx20UXQWyIkBb5YJWAVgEP1UPWsrBerBtUi\n" +
            "LYFxvX34k8HuovNRHjwGCVyIIC84edE1v0mJbqZ/6/L6lSS9fDn5pUR/u9n8hiAhJCHy6X3HHEZc\n" +
            "cQTnnMPYNYRrrkALyLETK06sOHlJaEI0dpoEHENw45LQDfrVX7v2q9d+5cUg29rc+PVfawtCtAK/\n" +
            "emKlfKKL5SM5XBhHzw+fO+bL137VMQ+/LNyXLFccrTgxdhdeOvfSYQUCTggSAgIQYxZwWHGAwBj8\n" +
            "kt1LFisQQggIN3bOsRs7N3YOTjiBsecCnADg3Oq3aOPXLm/9vV+9/DebAojOWN1yPlHvm4IVROOK\n" +
            "AwhbLlrO6DoszeH7qvLKKZg/A69qvv76gHz9qaDmgLRszdAAgTIMXFEY3nt40HvYb99ob93cZE/9\n" +
            "ltT2CSDgnV823m5rpfb2df+Lxz/44e7+Tw82f2Nro91WcQoBO4L1XIYkKbwqu7ZeZdaNKeF/wbiL\n" +
            "7H8zEC360wIgroi/47IXgvOVDiCJ6EqSxkmnA3NUFMYMXuT5YZ7lAx4WlgEESh7i7xqKwIlSalVr\n" +
            "pYhk6eHPnuYTyOfATK+lRWayRwfpWqqVAkwog7FoY0qiWCG9khSFSa+n6ZW493DfDgv4qEoXhAsu\n" +
            "SSKXnkdeg8B+Yb2ZYMhVrRFUlB81OuUAx+trqt1pb3XSNFYk/vpv4/PBayorZn1g5q7/vNmkOizG\n" +
            "UrYRpCRrWYJkk/Ln3O11ez/ts2NrIWUo8GiGFu6dkWUAACAASURBVBikl9sffm8niRPrN9x8zexS\n" +
            "ruaRD91jdiBB6ZUkjneyw3Z3f29wmO9+stu931u/1qYGUaLbV9M0TSWsnY6eWLTXyiOgLgKE79P2\n" +
            "v8XEYNYcUKqOJicLAxjZ4IkcBfFHS2JJZBmtBEjYtc3AFMMCzB7ji2NrjkwxZBKQSpEzqhWnl1Pd\n" +
            "0lQGU3IlIziEbw53H+wnaZJei30b6etqLnlZktC+lh48OgAXH7633U41H7M5yi0znGX4pGkwxyYb\n" +
            "FDwsi1VWNjxfyCv8GXLyeU1B+fxBCZIoal9Ptzbb7Wtp0iLVkOcThxc2/mtmJhCYcwk4/SFL4Wrq\n" +
            "z2UQ+P1F0dHnh6/04LUgFLz3YO/7P9yVRETIDgfMSGNKr6QbnU68Rhs31pOWgmOtiZnLak1z0qlP\n" +
            "MRURfMHvkYGAXoXUabKWGFM87pvsafa4n/WeZmhQp51sfLfTuZFqpWcsgTQR6edP0td0smSApAhO\n" +
            "yjx1ZMvSHzEY4eFAYCWZAYyYAVJIW5rI1/wmMBee/BI0KQuDoBLjCesnvYqRLLMUbB16nx9kWbb9\n" +
            "7nbc0p7z0mIZ5gNgEJIrKk10dtgruL313TYJMLer3chsiyFnA7O//9geG8MMh2JYwDEcMViTKpX8\n" +
            "gAAzsiNTxRpoAVKKiLZurHdubm69rUkAjiVsuZN/3sS/UqDUJP2FKsBTFQEV4k50B/P6/zk4O0n/\n" +
            "a0b8F8IoPPzgMOve3//0/p4xvPHedvt6ao4Kauj2tVQrStMUowJSwhbw7PwIUi4x2gsZKmqPAutJ\n" +
            "UdB7a610QylFGzc6B73+wNx5PBjs90zvUZYoSq/pzjvtjXYat0JpsLLoZF1FB2Baz3eC/W8WprmV\n" +
            "iBGpYCEq+YWqxEAZiliZtZhtAQGKJEVEIhwXHj1A8OcWgVBS79DC2dKkT8wWTfg0GDwsDh72lKZO\n" +
            "p+NPDemd7RZp+zwQkCi93mn3/7DbvXd354Pb6VriibkMIxAp6JZev9qxQ2bH7CwPGQgJRUElq0/S\n" +
            "/7SX5Tw0hWF2NlFJeiVNLydpi6hBugHAB19IuOJVNWtvDmboNICzZexaCAuw/HTK76bde+qc6klO\n" +
            "AW9AZDr5IAm9RxOpL7yrUb26C3mFMAliB+uIKQWo+7Pux//mbv9JBgLFdKe3t+WKnVtbG+90VFPC\n" +
            "FvY4lyVPCOHr3s08DwPMETCycBq2MqEzw5fHA0kqHJgNJTEfc+/o4LEbyLeVaiamKPaeDfaOTLeX\n" +
            "x2vJ+7c2OzfWk1gpcBnQVtTwVgZLW1nhl+ZPh7OCIRguly6Aqy9ffenWAcuVXyMxIuYylaaERETF\n" +
            "iOFYRzkigtWWCSKVAhYokJNOmMAjzo/47id7e/v5R7+1xci1r4PoCyUJVa4nTZ9WqnAkwdvf3aIi\n" +
            "7z7Y3//srv7gNqnUOEBIgiWAwLqhrICOiWEsF/A8iJBgBZ/eWzAE+7DFzbdTuLLugCAiHwnLEJW5\n" +
            "2s9KL97Arx/SfyLMMub1n6p//OXpBP5WyJPnIIBzJwys3erhRJSeOg4W+a6J+Uvn9nGbWX4b7iut\n" +
            "OBUECxAAYmYIkk2VZfkPPtvtPugWjpMrMZEshgUPTHe/Z/LcFMXW5laqCBIkiK0p/T0JUZVhsjxt\n" +
            "IgkBIskFuJYHqtSlgY8ZkpLLcZab3Xu7ew96PIQEiChONEGyKYojU3yeYWiLo8HGtTRuQbeUamgt\n" +
            "JaLwpnkU+GegZq3EojKY81A3IpbVewgneEZUqoRZpxoOaQhlLeCUZURoKj7O4ZikIqnz3O7e3+99\n" +
            "fgBhqEH5MDcF54dcGOgGpUlMBIzYOi8mkOer6pJ/Jd5Ikl4lubW5RSS7nz/+vX/2e7mjbGCSOL39\n" +
            "7tb2u9tayvxFrhsqPxpA2GRVQ4AtwBZCTeyConw00nC+2LYElhOwiflwGpa1n/G0OZvjzVlALvrL\n" +
            "YtGhc+Kgr2rqm6pt6Caflbq5q2qxEMo2NdvJwnvOomcR9SHHuHAREUQEAQgBh4sCFwW/dEIQ3moU\n" +
            "X8I5irQq/oK7P+39/v9y98c/6R19yavfUfSLq5BCf7t5YcUxc340/OI/fPHFs+eDL4fsgJd/6V4K\n" +
            "XKJINFzk3GiyiSiS7qLESIDdMSOSjqJjNx5iPIS44N4SQpL45urxBTF44R4ePP/n/9P97//BnjHQ\n" +
            "32nR3xK49FcOTrwF+raU3yAHN/gz03+afZHl5ksn31pt0iqodeHlXzpE7iLhosBYUJBbIeANeGfc\n" +
            "XNWKnlMiu+hw0eHisPwCB4GRgHPWwTmKLraAhh3B/QUiedkiuXCx+Ty3P76/f/cPPt376dOjP2X+\n" +
            "ktVbevXi6vGR+/u/0v6vf+ef3rj+S/pbTesYzkU110BxUWA8NUPnnF1xzRVg7BqNpv4bLWpqXGoi\n" +
            "ahR/wY8eP8uf9P7KmdVvcIPgxheajYb+dsQQ7i8dHGFFlhZoBzBWAESAxNhhhbAiwqdyVxJw43Kh\n" +
            "qqVdmd+PYsHHuWBgE2XP7hVR300Z6qxD5BYd1BHgfKzISvkJE15q6ruQ7/53rzKjZai9gB04YYfR\n" +
            "9C2vTvmrW2oasAnXDQT1O0LOIoLUeZ7f+WS3++CgPzC6RXGacIOM12Q1CEOGZXNUWMPMrBXiVrx9\n" +
            "LU2vpmmaaqVJgATJqgx3VB8aPqeEB2vBDGabG86e572fZnu9fn9glFJJK6EUUDxRRwsihtdgF0eM\n" +
            "IRKFdjvd/o3trZudZBV2FHLRhYpXEc0U85pS5S+F03Qxy8SHCDwzQsl32JH3kCEISEHWsRly/0n/\n" +
            "4OHjXq+fHxl22Hyn/f7O9uZ3O/0n/X6/37nRWb++DpEjKim/nKX2dWD/+hwTeUdMsM+b4HDws97v\n" +
            "/f4dNiZtIVGq09nY2OzEsa7KUkiiYLysQJQcuzvVdF02wxkVftPRPucqjTEH02x/qGwyIf4n0vYg\n" +
            "Bizz8HvlOb02nFv+X7Z+8x1NNGTVjSNCBDuCL7Hef/T4zg93uw97ANKrSq9qS8QhDhfhiyTV0qoF\n" +
            "M7SFMfmzweDJII77SStJLqt0LUnTJGklvm4TCaoc3STIes0C23xozKDInptsUPS/GGSHeX7EIMRX\n" +
            "0iSOSWoWOaOAkMEEBWYBNKFkQi3LhvNDk+9n+YtudmS3OknSSuJWLCOE3GGYpBs/HSYmwLMYBech\n" +
            "CMlAJUx6TweyxwYg2VSwyA6z7Its70lW5IOByWOlNm+lbLjX6/d+0k9XVWctJYetdrt9NYHLCy8p\n" +
            "RFPc7MLNMbHGV+AgQQw2eaYFb3+vDcLB/X73frf39HH7etrpbKRrbcDXXPUSbv3w8p/pEoxLRj4r\n" +
            "rszj+Wtg/hmGm1L4zUjvQSNYPyBq318b+c/n7TPd8k2dPDWPFy49Oirw7ugWwAgMMsx7D/e+/8Nu\n" +
            "/+lAtShZU1oDDbBjhiWSRFQ5h7AkANSkZFUzmLIiG3J/0Kcn0IqUUpokNVWikMRpnGjZ8MEqsA6D\n" +
            "F0X+rJ89z/MB50PDQ4BADairKkk0BAGGBYOYIKsc1XAlK+9AQpNkIiqOOBvkg0/u9h4lnRvtrZud\n" +
            "9lpCvkbUzMZ6U/kOFvUzPVIg+xYAsybFjrN+f+9h7+BRNjjMqIH2tXj7VppeTnRL5QOTtPjgYd7r\n" +
            "9ZJYb9/aSlrEwkx01VWWfvAkP1J1yXu0eS8ASXzM7HyVAWIg/zzr9x6nLdrc7CQt2Y5RGN5/lHc/\n" +
            "2z/oDd5/t+jc3Ao1V4N/rqefDOdTcZZMopgd8dzwleI5gPnMvCj1fGGfT/MFFSxiEF4b/7wWZEF/\n" +
            "y0+EVx3z7Pz/tJKPfAZYBrJn2d173e6DXm5YxSqJNTXJigIOgCWS7B3swCF9RU2oh4NKE7LMQ8uG\n" +
            "czb54QAAhiABSX0QkUCoPOu4GDKzTywLapBeI2qR0tLHDpSp70ypISmNW1U8bFA+Q60mahXmheEj\n" +
            "e/A0e/ws7z3qb99sb97srLd0tZivYlN93VQIBJB0sOCDz/v7+3sHvT6zSa/F27+9nV4j3SCQIQEg\n" +
            "T9dk0orTtfTOD7t7D/baqdIqBTFFsJYwgvUqt4D206EN5XBSMInSJEnwdntruN/rFWaw8+6mVgBs\n" +
            "p5Mys74W9/vmYD+788kuIDc3N8u1svAWGUh462O5cpM0qrNbdCZKbRl8rVZtO/N/kEosIGdj/jyI\n" +
            "mrNTeeFV4VWORi87LdDz134+08UpsLO9MTxGEQlYBwb6T7O9Xq+7f9B/OqAGpe+kkAThUVUybKkp\n" +
            "DaGglRGb/YQFQwCKSBGcImZyifd+LY5y71jm63NRJUA2FK3JpEHUJCLAh7J4c6BjClptSw4aunyB\n" +
            "pegBMCwIJKSfh1ol1sCLtDgq9j7P8sM8HxT83tZGu83WkPTlw0sK1iTwpNbVm0t/RHAEEER5cDi2\n" +
            "zLv3dnfv7cFx52a88XYnSXQcx5INYIDCe/JDKEuUXlbvf9C586Net9fT11NyRBFLIQGCYOuAEfui\n" +
            "nZU1cTYRMMBsAekttWAMjrJe73HaUuttTWy08tF+No1l0tBxQ3U/69350fct7PatLRuVee1HgAMR\n" +
            "FYZlA7NDLNzbS60AvPjXN2rwm+p20qedKLpnVQOBCzi5p1cYfjmZqYsWXxt4zY33MwvOW8SO4ZgF\n" +
            "DYzZe7B35/5e9syAkMSk4hRNYp8NhqEIEABJAuAku/LgbwBcsw5WCWGF9xcJD5uutuG9ZNxEF8ZQ\n" +
            "8K7sIrSXJMustQRnvGsleX8a9lbocCsDEKCGBE8ejQV8FlrtqHDIDXcf7IMHxmxvXk8BQBBF8DYt\n" +
            "Zl5Mns7kCDRza+1Fj4CICFQcG4B0k/b2ex///vfzQ7O9GW/d2kivKPJhzo6rfOVVL1KACe00TeK8\n" +
            "9zBLr/a23u3wCKicynwgeThW5ocvr5MGwI4gODvMPr23VxwNOp1UNRQJAzCzpYb0rsRpmmx0uPug\n" +
            "3723KyU6byfUCFkVLcM6IxveaX8Zn1+5sZ6F76zhRbX/5w1+ON+J4Bfn5MxcMqSBr8v/pxvvX8nU\n" +
            "t+KApfaDss1CewBNnQpjYHymYL5Tgd4Szk0Mh8dOuEuNIWj/Tx59fOff/us/ePBkwEKh+Z04TlPR\n" +
            "FENmIhLfEGLFOTgeO7cSuTHgbAOusRLpMZpAEyCCWLFwzn3JLCIeO4ydg3NwbgVuBewcVhwL5wSc\n" +
            "gLsEdwn8LeO+8YIaf4VLjEvsMGTH/JIdD8VLCCHEl879+ZCGQrO7LDhRzSbgXnLEzgHCQXjb0Evr\n" +
            "xs5h6MBCOPf/SiEvNJV24Gd/5p7/qTl8kQ+dINlYXU3cCMKJRiT4eOjGjt4S4iKJiw4ARuVqj6de\n" +
            "nD/cpiMg51Rq1Q8XSSDhEUdjakpx53/d/Zcf/0vh+D/97fT9f/jrf/s7gr987pxpir+KxkXjZSTG\n" +
            "TkCIMQnXgGsK14zGq2IFxskvnn7RPzxurP7t1b/56wRyLwFAvtXAWz6azmEF9BYJiJqhSxAAIR0I\n" +
            "EMWXQ/Pl8McPuvfvPUy/o/7hf7SeNFwkgDHDDeU3pGBL4oK8hMu/uMp/8fz/+BPzPH/U/rvr+hcu\n" +
            "O77gViCJnEMk4MbOLdvM3mI3RggKXAjjEh1W3GRt67t6PP39ZMRZAtFK+FQdOv/Piv/POURVXrgI\n" +
            "0eKzYnroV0L+szzAYuRftHyvifyOsSKEj/ocM1YEHIbOOVDvSe/j//H73Z88g0ByRaXXkoZeZfDw\n" +
            "yyHgRAO4JHjsxIpwY4YL8pAAxIpr+rNgbN3YCQEGnGNcagIs/FKPHY+de+nc2AFCYGpNHB9j7FWN\n" +
            "E6c5gnAvnfvyWLxk8NA5rH6rmf7S6rW11eSXLwM8+PP8+MuhW5G0gqGrO2w5AG4smC8IgL5JzW9K\n" +
            "SXBf8vM/HWb/vo9LUdJahUMER28JfsnkI6krzF8CNR+B+rWp9Z18HQl+6TAGBP7tH3X/+b/4N0lM\n" +
            "//Sf/INf/3tt5xyzIYqalyLA4iUwjsL5GLqIAOFWxJChVy+LS+Lg82fP/q+B+jYlv6Cb30A0FpbZ\n" +
            "OURvgS4Kd9E5OOedR8KUhINzY2DsipfOfMk//uO97v+2L4T78P32tTRxbhiRw0sbfatphxYAxpFj\n" +
            "J76h1C+u8ssX/c+Zx2Z1dVV/q4kxIohIEPjEbHSTsPTllL+K3p1B8hUsuP6a4Ca9lasale8omoh3\n" +
            "AouPszeA/HUQmHKQmHhK1Odbb71oQvOfs58ILx3gHIRljlaAFYEx+JLq/Yfs43+9u/fTQ7VGl3/l\n" +
            "Mv0N7YQYjofDsROXCCTcmIdciJULQjSc+yuISFFEIhI8lOwaK1CNaBWR4+GF4VBc4lUSEM1wuvuj\n" +
            "11k4R5ciCOfgnHM+LFxcEkJwCEd3DmNHY9dciZpwERfy/zlafUv8yrfoxi+tbv3dy1t/R29cvpD8\n" +
            "Al0YHR392dAVRy4iIMKYHCSExMoFjAHneOwAFpfgcMEJiG/qC0Lw2B39uTs6KpqXKP3ly00iGoPG\n" +
            "cCuCV+DGcOPpRY/KNAH1z+wbrUPt9Y2dG5Nl8+Dfde/8z3/4K99p/Bf/1T9I11pwpvn/Ufe+MZIc\n" +
            "WX7Yj71RvBfr7FWErhqXKfdIk6sZmTXiCKzG0Va3QANsgRZ26L3zkjgDd4RswAvry331R3/WB0OC\n" +
            "AcOGAAP8YAnUwbcgF7oDh8ASbkIaufvkWXfRmnXn4Ia6bHtGzoSmvBHYTjCepkMNf4jIrKzq6n/D\n" +
            "mT3rodFdXZWVGRkZ78X783vvrXj5bb+6Au8dnoEAvBrW5SoQSoALAF6wFEwE+jOvYIXqPynLouRf\n" +
            "ePEtWhuuDVbIex6swH8LOG530ZNuSAE5Q9XX/vGTau//mPyjH+9Yy7/5N/Kt/yBfRTNYcfDeeR4k\n" +
            "uml47YRfecYQ8Cd2TcnVX7vWfP30wT+rm6+btbWbOlnzXgyI3QkPVhZgaQuzIc7c8wP1N3yBJZv/\n" +
            "C2T+3ql6fH+K+U/OMCy+EfOL+e+LuT9nUN+9KE5NBH9TdUBEWeO9H6wAvyLgufi/n3782c5P/vGE\n" +
            "CNdu3KTVxL8qsCJ82I2/LbDC/LXBqyKhVf6Fx4qnV0kEGJbnwQmU8Pmfz29eW0uI/Il/xbP/2jNJ\n" +
            "AYgVCMCfOHECvwKx4nEyiPflgRV474UfAKu0IsWKFCevwA88G/6FHcDna8mtv5T9lZvZ7b+YfTdN\n" +
            "sqFfJS9WMMDAPvPeNc0zGmCAlQTw4oTECvtngxCyTL5NguBPBnwCf+LpV1ZXv6Plinj8/0w9881/\n" +
            "d+3acA1gIcgL7z27Z97Dv7IixAAzXjo5zfBzE3rW4+MT4AT39/7J//Q/37325+jOf7KRKSGIALv6\n" +
            "qvDe+68dQQoxoJVBA+dXvD95xcN7CA8W8P7kKYRvvq4Hrw6++xey5Nui+OMn+/sP7NNG/Mq/SZLV\n" +
            "1W8TTnyEja4AAK1Q3MS8d56nPzf7/+fhvX+6+5N/fH865e238u2/Pl4jP1jxtOL9CfwzP6BVeJYn\n" +
            "wp/41VdFc8Lee53otV/T9b90xVeHTePXfnVVKzlYYX/iBicDrOAUMvXyJGab/0oPUffNmN/5ZYVD\n" +
            "Tp0qKPmDPucDOMursDI3wisy/wJU8Hx16PQwVsT8GYJ35Bsxv2MeCEHAKxAQwn3N039l/uAf3//8\n" +
            "f73vT3z23VRo7cPSA3AS7GkPOLEihIBYecV7QRAQECcMD72C4XB1a3TrL/2Fa9eGa/pXVfZnNAD7\n" +
            "C+tXVsUJxAmEB+DFCSjIAjEQK1EoiGADnCicJAKEZ4QTLyEGK/7an9W3v3vt3//LeiO/lite+w5r\n" +
            "MR2cNHhW+xOfkBRyjRuumoH38J4aHxQKAe+xIiEgvs38tWeAXl31J8KDhEzEioTn5uc2Eciv/flM\n" +
            "JeTBwh89OwJ7rPiBkOJbwElr4eN8EXAm8/tnvPvT/Y//8CdE4q+/k+d/MUsCTtYfvQKG97QCiAF5\n" +
            "4mf+KGk8/CB0N1gRWDkSK96vPAYsXm3oVS9okGmd/VlqWD764/LBv3jsmVdXBcRArrRloDz8M++e\n" +
            "HTUN21+wnU7/t/+9+Cf/7P7ky6L8ud/6a/lvfm+sFSXgAbzwjRDilRXhTl6RKwPRNGIFjEaSgPDN\n" +
            "M7n2nSG+nT2tzYODmp/Z1X9nQITV7wj4QU/ZXLh9boXCuXJhxvwE+BfC/EFrW+T/i07VFvcJf5eE\n" +
            "eWTQF56H+U9O/dsH9i8H+fePX9j5ffv71LQuY35iCA8v4MB+4P23vP/WkTzxOGGxIv0J3DOqLf+j\n" +
            "/2XvD+7tPn6GtfVhsqYBjxXvn3n2jQirCmjBzwN/glDPiTzw8yPJ/s2h/mt/+fatdVojr1+1a4S1\n" +
            "b/trUt76TmIBcXL0b5qp/7oRzyDgxYoQz+C/bshj9dWBXAFWnIBfe9YMvraDo3qVp9/Vg431ZPP6\n" +
            "2ta/d+2vXFvLlVavCnEygJfeD7yXgxPyz1YlQJTQK5j+opn+v8a/8gpeFR5EEOJViBMvTjx7D09i\n" +
            "ReJEBOtXvAo8s1LJo+l0+vNm7c8pnV0bvEq8YgdiMBADv+IHK6/4b808Z7O5PjklBY6BE49QlUx4\n" +
            "/teN+JYAwMfw36KDh+Un//CTprG/+TdG45FOvs068fyLp3JlFScDYFVA4sQ3/siLxp9orMiBJ+eF\n" +
            "P8Hg5BV/MsBKAi8HK6veJ+JkQK+Ka7+2unbtJp/woz85LB4+fvB/PbG/sPZfi/LnvpxyOUX5r5oH\n" +
            "/2I6+ZOn//ywfvTHT+7v/fMHf/zEN/gPN9R//h9v314X4msrBIcF7V+FExIn8AKvrMALgRM4oQEJ\n" +
            "73nFZ7+a6O/A8/TBw8PyX1ZrydpQf9cLPwg++VeBZx4r3nnvT8TgJKzYds3gCP6V5at9NqXzPj//\n" +
            "nJyPlj8HC1bwRWfzc38HcQC9H38C327+v5yI3LJEqBD0WhoyPHtQM0iGQIxrBL5tAEHM2C/KnfsH\n" +
            "pWVKFekI7Q4Be+pDO2c4bY5dlhvoY+Rab1zP8vWMUBIAGOklgCwhRxluZmVdlo8rMzW23RLadF2G\n" +
            "kGirHiuwTChNtFYqv6azoVICXeq/9ACoTT0EoCQsPLRgKJmnZCxKdgjdMjzDE8W7IBIyNofsJkoS\n" +
            "AXKorLW7PyvyPMf1nAIWzLf9W2dYoACen8UCew+gn53QVrM95hCorCre+WLXWN5+ZzS+nRNVIfVO\n" +
            "ElEAbniKHTUFQ0BCtU+T4RG6f8FTOHMbhmISyFK9/c6WTvP9yX7xsJw8rJTahyCQzNZSIsnsjKlt\n" +
            "Y2CZLeeKxm+P7ryVZ0MJrqirNC3kLB7mAZLwiI2VPJFggEF2dFur1RHdKycP6rt/uGcaufVWKhPp\n" +
            "4DQkwwXkpPNdeK/NLA5Lzi8LoS31bn+zCP/cZS4dLGy/JS+s8Ot+WeH4U3k6czdD858tfqF35Hww\n" +
            "Nr6WERUnUFm780e7xZOaFGVJrMEcK0OINlG0LSYXL0fEgsEOwiqN/LUsz6Gogg9hanZgKQgJpMBI\n" +
            "HOSCeQjnNXwcjGV2JiYPd8KMBpkmyhKiRBKRJoZn513/icr+y4AyFERDjDwxyD5hw4aJ4YnZACAl\n" +
            "0U87D1AiZggwsxrqyvJkUuTrmR5m+VpIJiESMyE1l8q7/DF1hxAAkjAmlira3duZ3J9sjfPtzVHk\n" +
            "N9LcGCLq5b0HsyecxIZoxdL80/4lM9rP1pEPsZljb0L7D7g8DIApWFHOloQHEUY3aPvtfOvNjTRx\n" +
            "YAOwFG7h7M73e1gF2deO0DOB8nX9/ve0VsXO56X57OPs2vt0I4eQ3OHko0CZnySPtqv61egS1XGW\n" +
            "0VKBsuyd8/h8Gaq3U7VfKPPPyaSzGJkXP+oV+ZihaJbRXFlLj4hz9gBAiTLWTib7xYMSQDYkWpXU\n" +
            "B2wSAkS3TQ4J2TixvzoJR4KyIY3yTClib6nrIQ84sAzFGz2rBEgUAigtjrbNBQjIvCBWROzlyo0l\n" +
            "sGNIASnOkW5BbrAUlKWaha5ExYcGApJgIOFd6BI9i/91So3n0PxbEpWHdnK/2BiP8zUdy/6LtuYH\n" +
            "Lon8n8tODFj6/QeTnS921FBtvzUmQcxWDzUEuAkISBfEaIQ2A2e2i1ruUGcwk6dbN/L8tWz7HXtQ\n" +
            "VOWhKR/b+gm7BgC0ghqmt25nozfzPNXw4MbG7IbTiuX8VWZ17IMuA2ZPapjdeTuD3/30s2Lnix1K\n" +
            "3rt1PYPnWaP7JeU6l1/tQgzqi+hwP6vbIXsC9DLb+1L+P4WJfQHUaUfnU6tHXZVEzCeNODAfyz4w\n" +
            "4IgnD4uPvtgpG6Z1wupse4cIWb1B42AOe6AIOaHMnowl6ZETsiEyxRrGMMuY7yWdcBBwYAjOOIsL\n" +
            "yAf8LQBIaqtfCoKP7aaBCgJSQItYQ7aF/aIdUpwx7rI0g4bPrARyhfEamZrrhpFkJMh6cMMUYMge\n" +
            "7Gcl5Si0oDCGhkQNlXVVlPXWdR0wf+w6G6fXBeQs6vKCj2MWKgnFjnc+37WWP/jBOF1ngHVCpmF4\n" +
            "1oJISIZhAMLASwcwE6AyKqNNNCNaKDUT3/VoDRzA2zyh9M0cb+bcsG0MBIhAgkhIJCEnoqQEkpxr\n" +
            "GGASXX/w+Mha1cP1fku00lwn2oFtUxKpzc3cVOXOvTK/UWbrGUAEboGYkHOVfK6chPqy4L0emGP7\n" +
            "RYnQpyVZfb2TvDjmj2fquPp83u7nAl1W7Z+lK/RMu5DXZS3v3t8vDy0llA0z6nsEhJxTNnx3/iAI\n" +
            "WELRMdJU55lSRPCmUxBYQMZuvPGmwqlIEJKAAg7bfmhiyRGNG4fpZkvHh0p8sbz0mQsiekglCWRD\n" +
            "fSsFP7FVE84fuFES2ua54dThtSAGFEk95Kq2+z89uHM7VUMdygeHeUBP6VqYXlpM9SUahEYmYPD+\n" +
            "l0XxYJIN09EoB2opiADHLAVRQpWt2h5kHMHkggAyDUC0AE1dukJjkUIh4cORof8PUYI00SCK0Gk4\n" +
            "9gHV78Bwba8kjmuA5q9ADvNGh4dE6A5kpCAWEuBsqLY2x7uHe5MvJ/nNLF/PeMYQ84aDbwd7eY7p\n" +
            "+2W+sQiYFRo6xfmXov6qi1mAL0QjWaTT4WZxhgAAIABJREFUu3rcdeMg+hQe9uXU/t77M2h9+Mr+\n" +
            "w2JnUliBNCWs9jK1QqIet1rA3HkIgskTjISXmlSeaPJMDTIiYs0e8Cp2qhMOAJNrdzOHbuIpCgjX\n" +
            "O7kO2oZv1RMAXs4vo/5OQu0dMeAoNLQThGuZsaimlj1TkgMGsdhefHhx0jwAJikhiBIC0aQsisNb\n" +
            "40SyjPlDNCB2HPlqQflfiv87Dk+FzNTufP4pM7bfyimBFAaeAK2hIAhs5SxVAexdZdlamCPoVVaK\n" +
            "MqWJYgojoWd79zqWG69d3Fr7rNU62NpiKMGzK8HkYZilx6yVeNDRAuC/hf2reKp51m276+oEAExj\n" +
            "81xuvDnav1+MJoVWmVI0t1BFYJVgNMYbWULnsFF7/MWdL8/44tnklrzq0ZKsPsyJAHHJVt7xdBfc\n" +
            "5GkGuySdsq/6DqQ+DRAK7EeL1wOCnOedyX5Rs1onrXXw/3WlNeJWedr3IABP8DDTUvtKQREQ68yK\n" +
            "sNqoZXVuN+1+5gZFHSSOMM5/lNDBOBdty0Qv5/zz8yQFtwvOwYOEA0ACmaJcYeJMXTOGUFpBxLqj\n" +
            "wagOHSZDGlKsKbIqSXE1tfuTg9GNEUDO21m2zDnZKQsVQYKPUGByf78o6vGb+ej2LYKJU9cq2yyY\n" +
            "SDLYieBw5aKsqtrWU6dS5CkxSA81wcWEpd5ik7MVa9D1lpxFYdqZ9HAIOVeQcMQMQAuCIBMPjrVM\n" +
            "2VPVsG3YOSMHyJVViYKIODLJ7ILbJTYRgIMDnFJy6/VR8aDcnRxkeTp+fYPjZDrX8U/bFPCsJ/hN\n" +
            "6TQPCrg+94oYu8dS7ckDcN1m0L15plbQagEvz9u/LLx3+oDTzc/OHVEoAus8E9rcUkF7X0527heU\n" +
            "ILuWUULcOvYBwDvupqud4p7jkPnIYWpVilt5linAG4DBYHYUvVcd57c55/Fs3GuByFIE7TQ6ZmYJ\n" +
            "4ctXTG9yWkuBAeoSCkNcQFC+RkrC1MxcKpkHAYVg8PugiTgSslvQoZmstZjcn2xtbqhkNBfOXELn\n" +
            "uCCpPCx37u0qhe3NsSLX7ZyxEkbHDxSmEuXUlk+staiMraAq5pKr3HKIDpCAEiyJFM1X5iYTZriL\n" +
            "xcg4nGhpz1T5oB5EwSQlYAB4Yk+V52pqy6qqp8Z41gKbKfJrlCkd/AKu9fKGrE0Gy5jIiTzPNsf5\n" +
            "zl6xe2+SDjOd6iUy8lK1p6ln9y3ScyrZ531LtqW7o3cjWP4XeAHFLNX3zHO/FIPgTOpN63lIKnaA\n" +
            "FOSY2TMlCh7l1H74B5/WUx69kYZ3KGzRXVxQtCsG0UEw8/9xsF351k2VpqHZa2hWxaTAAhziVSIK\n" +
            "S8m67fHURgox74Bob4eFgWilQ3QckJspjf314bqpZoBF2y2LLQSyIUY3dPnIFg3s0ypNRpYtfIxT\n" +
            "zLDRrSuUQESSUlU+tDtf7BKpjds5QnU90c7tac9/dwuD1nMmAI/9vb3ysP6dH+T5dQ1fQrhgz1M8\n" +
            "3rabeQagsqY2qDxZklhF5XU1RdnYyaENbK8TpWB1ovI1UoqkICIoEdr1umC8SERzhuDaav9zcTvr\n" +
            "pQv1C0k5RsVsmStry8qUdVU1DIAFLKAEs2AmZEnKnhU0QM5XsiuXEhx73hGZrTfzoiiLB2Vxo9hK\n" +
            "tliAhIRw7ZO6qOl1XGlnGAUX0mm/QAeFmNOVziXfSgFx8bHxsr9cJj9Ny6TpOTPogOD18gTIalp+\n" +
            "9Ief7n9Zp+uktO5Xy+ZWh6Twq5XHFELlIYX72MFzOqTx67lOiNnoRFECrq1hlsQOOoxHgp2Pljwj\n" +
            "xttCgfdgCnLjOl8duh9A0rwk7iu3APpC1s/0IBJAQg5OJXLrzREA3ivLmtVaQNEEbd+FsMWcrCRA\n" +
            "kB5oJ+zupMjWs9GNPBQLoAVn1ZwImBUR6QZT1XUxOSCBW7fHIb6gSVInQWKYNpbxYlBZ28o6eDC7\n" +
            "EDMHoreF4AzYCgdXq1WqatIJqURmWimltTBERAJS6HAHzK63zzp0iCMA0MEdaFhWtiorLqdVbbm0\n" +
            "xjnmAYGIBYxHObVERjcZCa4flekqstg4jLt7jMScpWrjdr7zRVE+LEejUUYZJ6Co4GAmN8+nb+jV\n" +
            "W5AvPVUfMR50xiXEouCAOHO0M8+Uf4lq/9UjeReRBBwchaaagsq6+uiznY8+m3CC0Xgcu7KgBbfM\n" +
            "8xhC7MqbVihEfz8aRkLWoqxJ0S0LIuZqakhBCiIKHjNCgOUJE5u6Q0VkUez/oUDkOl+cByODJ/ao\n" +
            "/Snry7cBfwEAFkwiuqy6gBXgCMwNICgb3tp+e2yp3Plih4+g1hQEmG0QNOxBCUVIkpAghPb16Wt5\n" +
            "8bNy/0Ex3qzzVFG7PqKDoHtCx/F15LT2pgxjd7JfPrGj8SgdEnylKdTJ0IAj0XbgiZ7OvJo6azLD\n" +
            "ZL01cICLVQmjcCR4rrwjnRqgbkLtM6emJJVRDVGCW1mmVEAuQQoOoopgoq8tCGtoRu48rDXl1JSP\n" +
            "y3LK3IRiTRoyrjnTuOjaZMkGurIHX5Zb17VeH5GwDiz7D0RIhZI9jV9TZYHyUWUrzlMFH+RsP2p4\n" +
            "9WjfJel8teIikpCgqMWEs8mLin8E6XCmw++XpxEsufMz7KsBAMlHHPxY+w/2d77YB2E8GkGAPbMx\n" +
            "VW3RcPT59QMPfqYLhH8DEVBa/vD3JulwkqnAt1AKUpNKiIg0SSKl43hs9EIHqcyGmcFMiQo9LENp\n" +
            "IGY2zMwwR+AGc66+4P8XIJqVr4m/BwgYAXnM6Kn0Tqd6mBVNbA1IRJDEDiSJkqDI9FTTyJPI1lQp\n" +
            "YK3hxhDU0n2jb7bMoBce7pirqSm+nEDwxhsjKdiBkJCz3DtP4EYJj7Iu9x6UpQUDpovtx6rYjgEI\n" +
            "SVHZ7iKg7DzYG1hQwyoha2sSRIAkKIImQIAGDCIKjR4FM2AatkeuquvKOtuwCe59SAbYOxzD9YKy\n" +
            "1dQQqJqasrRa8Ea7rGasH2SNIq45W8vzPC/vlcXDIh+NFC34sGel/s6k5+bhpe5tH+dryWHLztC3\n" +
            "ji4OAJ7v8FsqFF6WRJibtTOn2B07GX0czJ5sw9YzKUJCtmHj2NaWp6wEAVDBnRPtOkBAElQCSkiK\n" +
            "0IYTADAgIipLa2tT13AeFpZADEsUu7hKgdQrALXgGYOFoXqwQKqCPzoE88h51kEdjj2/KUbjgsOJ\n" +
            "mUScSfIILmsWYdfV8AAbBlIBCRjPFTN8aUXGTJTU3HBA1MqEyBORZDhF87BTQbZhCIRioSG8Fwbc\n" +
            "i/N34pX6Oq2DY0ZdluXjKr+Zjm7mzheSQAg1AkO5W9eCC2HYTR7YTz8vWRDSjIPhToihgdYYDhXT\n" +
            "I9xQAEKHSzuwFIYEwI7ggt9Et32mgoeFhApuCCbLOOAGjhmemIh9hFpyw4gVwUIskwyYGZKJvLRA\n" +
            "WfJ+UW7d1hQgz73pIm9D3fTRjfygcAcPDvLro/Fm3q2Rds7ONqRnweMzHX5XJdep+oHO8fZjUbXE\n" +
            "ecfO6Jdo8Z8VKbm8sPQBB+IkSSKoTI9uZLtflWVRdgy5tTkaXc8ylaZaB5CJ8+git5JIKa2JQERt\n" +
            "V2bLbKZcFGV1WFnL1VHJDSNpsQmMmebf6cseIFKJ1KuklKJEhxrfRKRIMpBppQghOyXGCz1CpHoG\n" +
            "zgPguW0WQEFGOA8cWQjoVQKzMaaccl1XpWHTaNuwa9haCwELRgKlSCpiCSQ6ylACPKqy4hCpCNtX\n" +
            "r7T57NJorffgbvAEFye5/KoEMH49VwmHSQhFBNtaifEM4fGZBrUFqS7oGD0vJBAiLySIw84k2iGJ\n" +
            "marRmSTcYiIMd/hFJ4WcBd7JzZymQrbOXY7+3eOoQEUto/OtEpSi6gnv3p+MsrEe6hDTlQIQ0nkX\n" +
            "YFpEyG+qWyO9e7/Y/eluPlJqqNE2R7loZS7O7gujzo3X4QXEpZx5wMWayEtz+C24LjoFfCYCzoqd\n" +
            "zIfBeiQ9kYvILazi1us5xB38/s7+l0VIPtl6I/vdv/leti6pY9Rldxc37ai4QhNyhY3rueOstpWz\n" +
            "irltjRsOFlJSNEfZhxw1aCJKlCbpvGub1QW/GoDWkRa9jzNoAABAzd5cDrnXYeTsAOQB2sBTU05t\n" +
            "9RT1tCoPTfXUVtZYa80hcwID1inSLIOABJVVJWurCQRGY+GzmeNqgTycNxIgkXHDoBSMyZd7+w/K\n" +
            "8Si7NZK2KbLEEcANKSIGEIOgxMwuIcuunBonQKGdIYEFSDCDIEIoTYb9nEgycxspiIkJUkD1bTGB\n" +
            "FhwRkh7lLJ4q0GsW0j4ainxORO1aj0/KwTigYpMJqa8p+Lp8wsWjcktlIb4Qep8SmIViz0BFiR5v\n" +
            "KoO0eDDZ3dN33tmWKgNi5CWu4GVuuZdBsn/mU5eQy/69sDPnAv1Sdv4526l7p3NuX8WP0i0OINUK\n" +
            "r1FxOy8eFqPXRqY2o1GepZr6YmWZk3b5VQVkQnmSI826/bP9iADMoLLdeY4BhAU/T21rMGr7cwei\n" +
            "Y+4fMzvyDIxdHH8AEaSZTjOAnB9bhrG2flIdlFVdl7sPirLm6rAmILumTVXVh/UooTtvj7mxRVHk\n" +
            "1zMkFPSOOIc9kgASQFI1rfK1vKyLT3+yw2yzGyOV5vDO+dC+XhIUh2LnIaYlUghlGlNWFgAo+OFB\n" +
            "gkCyY2nufrMLUGtE6dDzO852sy5taT4va3686HocBAEugJAdwMweHFA9pENsxgGUaKXAbItHNl+3\n" +
            "dE2R0CZe3LGXlBAzMSi7no+OdFHsFEW59RYyGUoVOykke+e6FOmOFhw6L5aCS8LPVeadm6n2is+3\n" +
            "hf+pBvrOMgTOIBIgQBLRAKbtkG2nhoi239zaf7gPD2amZF5qXkiDefYT/RzVbj/hGEc8fQtBI4hH\n" +
            "n0cz31HvnmiZrhh3MwGOFeY5bGg8iLyqEpWn6eiNMZhHP5sURbn/4MA2tTNc1YwGd35r6/3v39n5\n" +
            "Yufg/v749ki9ltNZHeNWJY4dw6qhroz59POdvS9LEpj8rNaJTDOVKxUmwXgAUkZMAQXeKytbT1km\n" +
            "iki1UyH7WUwU5jDkNy191kJCdNXWZTQuQgSkHTDPWRyEFg7U6o8d/I7gZzX/QwgzhGOyXOkMppxY\n" +
            "BkRw4Dp4y54YxA1ME5ovVPUTw0A+yiHIHDEiTMPN6aqL3E5RBr0MLaCdtLmslhd54j9NWmopLecj\n" +
            "21iImd+eGwPP22+NVab0E6rrEl2zR7F4orlNeOHFIKLZIw3C+uvD4Be3/fAtGoCP5z4I8bN45gHN\n" +
            "dvtWHZinM5pqH8dThbr+QLxfBeYBKFxRsAQh4Q/e2TZvVHdTuvvZDnnO1hXd1u+/vZ1fy0Y3s/IB\n" +
            "uDFSjJg5mN/xErOV6gwsOal1+tHv7+zs7aapgsfu/XJ/UukhxrkipTOVZddSpaATTRT6EZBlsr50\n" +
            "gFaaVjXDwcf8Zkri5hygitLDdE+kDUm0YDvM8JrLnk9wvHGPvWfBGqEQoBc+ojBDUQU+gjMRn8uM\n" +
            "8qimtTRTGiIra2d8Bc/Mhj27huupMUdsG7gGMVjjMaqd4yDuKUQQpOhv+qfXw7IV8k1omdI6n2z0\n" +
            "Tdn3/w/Mf2kKyk9M7YKUVFqrFG2//S4BdZrtT0pmB0fOs2xXeS9PqydRZrmrjKPZyeMSdDP3RE92\n" +
            "UD9UviAvYlec456Nc0pNuHznHBpEYcGu20/ajEAROmd0o4KpSlJqPBqVXxUMbL+1OX59g0iZp0Yp\n" +
            "pbSujI03uHTfOAYNgGNXPi537+1t3B598Ns/NI29+/mn+/cm5VdcPaxZ1EoUegg1JL2GVCmdSJUo\n" +
            "JFlwDcbpEjIq/KINuocxe3b9vToG/0GIzN/a0m17ng4rKfrtTEKgtO22EtBFHmDnmG3DHaR7luAb\n" +
            "xAQDHqUoswSmwd5PS8wl+iIT0VUBAUqIEiLG3Xt7hu3WG6OtNzfUMEhi6pXxOc3nL8Xbt/wdEQf/\n" +
            "DemlMX8HpVq64DpOu9KUDRB1MA/n4Y64KAowZ0PKVFYU+wR2jXX9QkvznD+7WAduGRAGFBXFlts5\n" +
            "GPnzF+9gOdyeIb4ZUmK6XT0s4rihcScjqPetS7bTjFccABIQigA2FSAxU2uZQDohAmNdj9K0+KpM\n" +
            "hdQi9tLWiSaB6rCsbJVlWQiCz3ZVEUxelgNFq1nxcJe9Gb++rRKtlP7hb33w3jvb8Kjr+qA4KIqy\n" +
            "fGLLrxhfgVCH4ZGa1DWQwNjKsNOJpIQA1ppcayWFeFXgYdcw+barEqJoUCH+1/S0EkbsUxSUdh/a\n" +
            "EDluOCC746LqsBttCnaI4UkCJQQCW3YMvU55mmVaEVE21K4r1C+QpSkALaGUJpVpIgjFbKva7Nzb\n" +
            "KR5MPnlUEnhrc0yxKFi87lxkvgsnvViatyxiMQ9xqphHgANciY/bqgpzIJ8X6fk/fzqWqTTLD+sR\n" +
            "s4n+XgF4so2pDqsszQJww06roBnKVdVjj+VqP3o8TIP5MEQIiS9zwvHCqEQ8w6JBIaMs6Ov53AmL\n" +
            "y9Os3wfgeAYyGQCI9XnCoq8skyBSGscFt8cTkSbSQ1Ue1qaus+C4nr8XAkCa4PiIq7ImQfm1NHAR\n" +
            "KVJruQTyG/n2X910HrWtq2llrOGndVWX1jqG1ENTMWwDM63LOqjoXCWhuyG12ilRQiQByCXijzjw\n" +
            "sO1gCEcOzCFGy8xoutQMQJAmRQqUUFe9j6RUQ5UprROltNZKUUKmsXVljK3zYbox3kjXMnYhZsZE\n" +
            "LfJCasDJAA0aEAB2IKHUa9n4tXzvp5NP736099N9rdX4jQ3HUfm/Es1qGYgL3lxOZ/sRLp3Kf965\n" +
            "/20iWgUfxekovyptY7aujUOtPtdtCFevURTU7Bmd37721JlPWxMX0ML5L/OtwPCnetcGr5hOCJ6k\n" +
            "UAxi4yAUwFIAnkc38+Kron5q8hssI2ylE3aMAHY4luQJFpJZC6lD0RuBIEPD1q0FyVSl11IJxjHI\n" +
            "w7C1DPYwHlVdTSb7xaPaNQ7MtuGAy3UR4xAT/oOdH0x96pIvF3aCqPOTjAV8FKVSDynTlKZZliq9\n" +
            "plOVyaicxTwLRTJE+yRCPSUwJK4rIEfIa3BVZ0og9FAE4TgkKcesZ+cjoCPc/ubrIyU+uPvJx1VR\n" +
            "jm9sSEFLklBfTpxvjjol38+V5ZStZ3ThYDn/bz8o0L65zGPwnJUGn49Ob/49DWf5MIRk5yCk88QN\n" +
            "V49rAGqYyYRoELJ9EBD+V6NLd6qehaZa4jM4f+kOz1fybS77+lnjCpU2jbXwIKmjOr1K1VPOhlmq\n" +
            "tJla9ixbSxvo6aunli+JDjJ86po9lUqTkgkBSAfI19Nbr+XGGm6YGzZPq6rz7TNsY83U2COuplXw\n" +
            "qDlmZiaiLCHVuQaFVJq0IpVonRAlKlvVpJQiKSkQSACSpJDwzh0DbRmlfqp1ix3qA/gRkIeIZe0W\n" +
            "nuSyTZQhBemEaFWyBzcsE4o+F1yB57uVfOViHgu0xP6fezp9bO8l3QGLYwn8/7JsgUXqjf4ysyng\n" +
            "mCVlErDM1dTk63mWagDsWBGxZ3vkXqIIGwCIRa9egpF3GVomKAQgiLukfSIX/G2eAKihztbzqi5t\n" +
            "Y3Si0Cn8sVBXGyoDQRCB4F3Iaw4xcGBOqElQ2PaD4hBG48A0oDRBmmg5IBwT/MhEkJWEd8xsGmZm\n" +
            "a41prH1qKmsBZMM0zbJsSB0AQZJUgcsFZlZDRGOFjZfhGLAIqVbh4wXkj5g5FNr77VX4PRVTaGsA\n" +
            "hst1RxIJBIFjpszsEEKeL+LJX3l9XlLWXDF2vuTABVT/S+SlM27pPLlFQZnk8rCsp3b7r25qCmoe\n" +
            "UyLDR5d1KDwH9XSE08CepYd9owvNmQMLF+zjhSiwawyJhRJXAtbUUpADsmtp8fCgelKlSrelrxgA\n" +
            "haSDFpUoiWaRke5FAEG0mb+ncJnMgBwAYDeAPAaOOWgTbUEMhgASZInCAM4pbi15BikCJQpgObeJ\n" +
            "da7iea3kNON1qLuOQgA4fktGj6lHl+7WHTNPZ4jUMHgCs2UBJ158pP2M8XQDOPeAJZD++MKJS23+\n" +
            "4ipqwounJfkHZ/Otg5MD6Rxsg7KsAEqv5U5QiCrJ1RnA04Ffxi0tonRO2+qnIfTzdDWH3/FFpkQM\n" +
            "NHD0hxHBg41xHppgGpdlCoazYQYhi6LIb9xSqyQH7FyHpUUH8ncA2mp58yRxDLQAR0LE0pMPpdDY\n" +
            "OUiJLrbaonpaVuymxMf65RjQ7G6OedE3LOb9rzOaE+sEMHo33ie/ALliIFTmOIvOKCwKIpJ6qIy1\n" +
            "bWmm3lAvwZwvgM7n/AXf8/ynF7KAuPDIX2q1j/Ov5aXxjqAAGGvyNMuG0YMtxSw/JIqASw/7Oezw\n" +
            "uLi6Hf7SAfznpdZGXdQpAp8QEPnKsXVsw+qXFLC0pBJSQ3XwqBxPK6UUJEkZJQsdh+Q7lq060AHZ\n" +
            "GAAkjtvylcdEIEgbR3OMgJ8DIFfb7Km2TLgixT7E/yUt7LcLqU0R/4/O8xfQgaFQbxBP1KUJC8w9\n" +
            "mVhVqZc0HR49EUDgrt5mdNTNI2Rm5+F5XSb8iRn8CZGSbGsjmETr/mxv5DkUzNN69Hm85+cOO4/8\n" +
            "qX8vMbbukMUx/HJ4PsC/2v8usKakJD6KZSRsw1macWd2esSw8DIP1ouiBTfRoiKwYBScrRdcgU5t\n" +
            "/rONtHtTgAawx0AoMRhMZWZajd5gIpmn2eRBUZWlVpQPs7g3DqLUcANyoJijENRy0V1NtvdHsajZ\n" +
            "cayHSwBJYrSlDcPXj1u4ROR512uyJMEOJNVAAz0QbpAXgiKWGQwfCnZH7u1zPqGr4Tf7LkniriWB\n" +
            "iEXHIXo+NkEAyZlvnM5YJDynL7SXsI3jhjHseQpboMGF1I/qXalY7sUn/8brXLR16a84sqvTud7O\n" +
            "U3Cg0zEVAMeOwJKz8qsCR6QTnSVM0vExSz8yhgiKRAawpD4sfHYC6sH4Q5lnxGW6eDluA7/OsV5V\n" +
            "5inLBLsPFdvyzq+DvSXiapBSksFrB5JVQQJAhVVlBHAkpaeujCejY4l5lSK43HoQ4CUCotdpozfK\n" +
            "/sYFPgIE0apyRAy4kMbvQKuEY5YJbY3Hky+LvS/2N25uIVG0KvnYhHYnGEg41pJ0iKILAlTrMyeI\n" +
            "FgcRMAXHun1A4FYTiQmUPZiThSVkHdZutsXEmsvzTCjYsZNhwGgr8POsdEIL6aX4XLoTRg5kPppP\n" +
            "u+BYhEd2T7Z91u0am3/cUXFo3xTtTXliy9Y6JaQWRHiefaW/4Jcu/qV5KKfVgfnMp7lafbJ7c8Fb\n" +
            "133pDAtF4OWzfaCzVYllQMClQ2oTyOuqgke6pnrV8+O3AuzzzCv1kDZLrLhAAYQf9FhJUhA7lgTH\n" +
            "+Ojvf6hXMV6/la0rWgVY1lW1e29Xab19PVNDBW/sMRjQ/TZBETV4xuW+mfe4z13cdjGCB1YJbrZY\n" +
            "KdGj10Y7n+9UU5OlqTFGJ2AAAznrMDjoXs2xwQvwX86Nlxb88xhAtqBM7hfJPR0JXroldGzf+Q4C\n" +
            "QDNWcO3t5Ivuw/lPgTnLoKc5s3cRsPgykHz+VJXuF5LDc4mvr/xyOP9MulrUlCSRYXPwVaG0zNYz\n" +
            "KQgitmeSdNk6KmfF22eXGfQWx4BCQe7ysCwfltVjay0AzUeEI1RP+OPf2/nk79+tDytw0CY0od0e\n" +
            "B7OfzsfF/fcXBtJns8Epw8GfgjzGIVJQKBx3KMAoJTvVWhJt3B4RUVEUDhwde45x3BWoDVqJdMyd\n" +
            "kkKt6s9nTxqd8bOcopexrS+E6HF2HddRL5beu8TpNy+kGSjw3KPOWguu/bg7FV6OY/ybA/WAuX4+\n" +
            "wGXnauXiQ14qnVrQkcSyH4AGsjwsikcH2fU05o15wMN5JhkKWvI3FM58HBQEggg6efBj02Syz8zG\n" +
            "mqoy3IA9gRSAelqXh2X11FjmNvFjYT9hgDFgjo0F4+9zeQqnOeisQ8P7rvN3RB/uHIaCBNI0y6/l\n" +
            "RbFvp1YnCrE3VotEDlChQewsuOSKZz2py9Ayp7QLCRo8OyY4bszUHvysqKzpvnXRA22HOVsnIdzI\n" +
            "sa3LFUi2P+1lQyG2Y1zxPFe+5Iz8N5hq0ZeSF0uqP23mX0rR27RkO2HniqIAMBrlAJxn51vAdhIy\n" +
            "zPlUpGc5hSwwnt+ouDUjAcYgLFCmAezUFkVhmK21xVeVYUDoAP8gQcwwlgECKXgKK5oH7XX6f2ZX\n" +
            "v5AuLcR8P1oWttNYli9OnAB7loJuvTYyU1sUBxi0y2sQamMAxwARHzs+autkx9O+GEU3jqSDJ0cz\n" +
            "m0KVwZnjibk8LHfu7ZSHZSjUd4nb71ilXSdznLPolkXr/+uhm87YKrvFEEIh/lyT8rlJLDAt8Fzq\n" +
            "gDznvzNoBaHQ8ovRPl4EnaOxCF1NuXxSjMZZvq5jQjtkcGyERK5TN3L26vEd2yxb3x6xKIAgPkZR\n" +
            "7FdP7K3rGREdlLW1znmq63rv3i437DyKJ6XhmdvJgXBMDGIQHxMf05xKf9z+4Oww4akKPyQWldh4\n" +
            "sRa/HZuCz7bZ0MQKjLi28jzTSu3+0V5VWcxj3UJ3WgT7dul4rq54L7+pyKsEEDPbKbvGxAbkAHtU\n" +
            "T03xqDRPrWW4OU/Vc4uhJY/49GSeolavRCcv/m2hy1onKy8XonARLXocZk5gXvIDVE8q87TauL0R\n" +
            "oX6YCX5Jrd0ba+bikpiakGoyZyIKgiQMIAckAWPtwc+Kaortt7dH13M7dZW1zKhqc1CUADShMpZb\n" +
            "a1lKkt2uctxpFnO7DS1+ekUKW+ig9zruHty60+NsmMZ0KU9qqPM8K4uqPCzjgnb9aSKg637bkp99\n" +
            "duZAB8t++jTznEX93AnYxk4eljuf79TTuntezKitraZcNyFjh+NOfo4m3LkPunXS+RRET4s+0xnE\n" +
            "vZPPNsK+B44GF0qK5yfX2jv9G3xe58KiDXE+rUDIhe90bZWdf65AwFWmaVkIIC7lCPDGTOi6BpP7\n" +
            "pUyQpUoSoyut5alDrVrL8BTQZq5hx+waVquq/+yp70MScRPoP13nGQ4kyB0xBlQ9MTt75Z13xu99\n" +
            "b5yPtPG2qgwLKsuSG77zzui9H2yyXQIcAAAgAElEQVTWtiqelCyYBNOxBQgDTSIzDQBdPuH//u99\n" +
            "9Hf+2//h4GFFA3INgivxfJ5aQqH6AMCO2bcpw46lIElKr2rEZtXkmKvD6u/+7b/z4d/70E5NcPJp\n" +
            "Uu/94H0GFw/2Az8ET1uAyrnGGmtl2yiKBFHHV/Muiv5Oyq2XhF37cxyrG3UaR+8OCQIkFTeoavfx\n" +
            "Jzv7X1UIjsaG4dk0Zn9SWgvXhM6TKkg0B8bcM1pQ2bpOpwuCAHOCIJBHWDm8sEOcki9xBXKLHbpk\n" +
            "Jd+OxKJB0fGU6xQKABGJMPvXBR/oGW4viZ5Eg5NXVd17F/qmAm1BOlyteN5p6sI5s14UAOIw69oc\n" +
            "PCryPEtTDQk4ylYVHyOWWxIAiH1IGrMQAEESuYarqmbvdNJW1z0jP6cDioXANR8xAHfEe5NdTZzf\n" +
            "GFFCWpFMiAFubFWXmnj7nW3z1EweFNVTE2fDO8N29/5enqW33rxVPio/+r2Pdu8Vo9dSbqw5ar0A\n" +
            "x2hTaC5L3GJgwnYEQaGgDSTcU0tE1HYrKx4Wf/e/+xCg3/ntDUpIryq0pS+23xqbhqtpla1nEhSx\n" +
            "DwIhRc80NoB2Ysyvhdb052o25kFvGvt4ilkkYp4EwGw8A1xN68oa2UhTm3SoJCnHXBRF9aQCAM/s\n" +
            "HXwsytSaJL0rLA+d8tw154TFRZPbhs0DuTAGdsxMJGVCofniFTbWU7rGIs7Hn3oRDut+nSIXwoJi\n" +
            "1qIz+rxO1/M+ixP93CEviC6XS3AxdUJxJh3jkzMWdc1bb6cQMEcVmAyHPV/DO8dg5qpi07g0VZLg\n" +
            "LLsA+EGLPA/YtWNavNaSAZBjloLKutqflPl6vjXekGRVAghU1lZTLh/bNFXjG3lJQIKyLuvG5ooI\n" +
            "mPys+Ic/+nhzvAGSn/z40517Rbaebr25ka3nUqjQfCYK70s/gVPLnNrdG2yYiGzD9RPLN3nni7uf\n" +
            "/INP0uvpD/+zd2+9PnJs+Mg6D0lKp2rz7e27f/iJmdp8PWPPDELbUCQCaaMk5bMuPaOLEhwZcwKO\n" +
            "BJn2pTXOWssJ0JbEZUb5VWUbZqC27DykJOfQZmq0YfboDz43rDtnol/E+d0J0UMfMSBCgyCTrmki\n" +
            "PQ8TOIVWmKezynUsYntPn+H89eBbRrtwl73EHvximH/Weeq5zYQzvth2IJOAK8vSMeXXQnkGQqLr\n" +
            "J5YSFfvVeCZB1dTs7u2xzfPrqVI6FDyIdqx3MhZjCsrFbH9aFshhAA5cPirt1G6NN/QwIxi9ShBk\n" +
            "rSmrihu3Md4AoIdZtpaXj6vysEpfzwkgQY4xKQ5qaw4eFFtvjt77/p38eh60j5kbOS67K7iyCMQi\n" +
            "VrkJ0lZKZau6mlYHxYHzvDvZh+fxm6MPfvuDLCXHrFc1O5YUXitmtsYEd6DzkIjdzVzDNrgtZjzW\n" +
            "0ry/jZeKy/7cnQG46IKR7Ll6UnPDlMRdyzGqaVU9rSkh8qiemth0SMQooLzMLM2EKV/Nnopf6UEA\n" +
            "O2PTc2zwJGh+ZvpVnl8cdZ15lrKmkHIupUdeSt/3vWNE35vxIsgFgfR8E3Em2ztJOiJEBeyUiwdl\n" +
            "vp5naQaAPdu6/PSzfUnZ9lvb6TomD4pqaplRPDioy4N0TW9/bzu/niM8PAleqPDTPrklnN/FwBs+\n" +
            "eFiohEY3cwAAU6KJdPnYQlTwGK3nQbMgpauiKMty80YGgWyYZakuflaWh/XoevreD+6MXhtFMRSK\n" +
            "i878ald0Yg+C5tKuwgGVj8udz3f27xe2Ya0UN5zfzO98fztfTzkAXXvExzyZ7MNDEiE0t8Ks0ODs\n" +
            "9s/X4/px+9ns9cyCZWCk3hepqqvyceUYysMeGfgcHmVZcuPyGxk3gDfGWmMsEcmuLbrv79Ln0Jwx\n" +
            "dzF/BtG2sEUj+oMkuqxBnh3fU0ivSn2lIBoCPX9Ey6bLLYwoEWbMLKWQzx2oe3Fqvz+7As+VqHU3\n" +
            "Apg1n/Uon5TlYbn19rtK6dhiXRA8du/tmgb5dRRfFfn19NbNW6Ob46oqdu/tlmWVpVnstBN8Y6Fq\n" +
            "VVtpl89WVcKzt42rK5tdy7LrebhHrZTS2eTBgZlOMsG0qsLxWZqSoIOHZXk71+sZJdCrxB4qwdZb\n" +
            "G6PXR8GL0XaJmzmr2J8C/J9FQR6F4YWy4scwR/bTz3eK+5N8NM6v51mmmaGUunUjjxB6Dz4GCTLM\n" +
            "mlR5WJaPijzPldIzpcMBgFaKiNg7BnQnns4xTJ7jWQuSAg6oqrqyJqgAoaIme64PK5DcenOzfFKX\n" +
            "jwwfOfYgD+cZ7GTsR3iBvt1N1DfhT4QpYeZQSlAA7Obs6ouApJdnhG/IMt8kRP/SIhjPR61CJYld\n" +
            "E9+TgkxjykcFhNwcb0qqHbsAWdt+e9vy3uT+ZPdetTHOfudvfZCno9DFxTa73LBWaoaBEQCYQReX\n" +
            "4mnbe5qGjXWjLFdKxYsqrVXKdr9qbP56ppUGQwoajTb2vywmRTG5v5/TFiWZA5zHaF2N3xzLAWKD\n" +
            "3dZH7djJRNLyYv7nUFD4Q50KMmzt1BSTycab4zvff18lCr7T6+KqkEQAG3ZSSGa++/mOqe2dd0ZE\n" +
            "FNHvAiGvRikCUJVl9aTEetZ2y+6eyzzXid6L04HVcHzLIdR7nz1sY+tpxQ2HymsAOYa15qAss7V8\n" +
            "PN5i3t2/z7ZhEsQe5cN9a3h0c5QNdZyB+UIQZ/LPnJV+dZvUgxlSkEr0Eo/ai6Il3j6c71jsCvi6\n" +
            "Lpnn/OGd8ekyhJ8/9a+PmnCnsbQRi8UQw/MHCOcoKHjSgEDaeFRe7h+yUiZfr10D6aWEBnN+XX/w\n" +
            "W5ubbyn2rJTKVCZJMtv9BwUDWmWh750DOd/Gw9HBbC8cA9dVZadWCifBEAwBSZQndUp26wbufG+U\n" +
            "rhMESNNI8Sgl5bn8qqyZmY0WBsDoWpYlEo7BPPmj3bs//tjWBlDMElB8rGhACxGHDnTQjmMutCYB\n" +
            "uaowUM5Br+YQGSWZsWzqCoIBK1FJUUFa4y0EYaAcKyB3nH70491PP5vkt0ejNzcgDAYMyc4bucqQ\n" +
            "xF7qbHz3p/V/9d98/F//7Y8++Xy/fFIZD9Jw3hCqqBu3saJZlHIB/xNV05kVEFr0MgCQacANl1+V\n" +
            "eao3Xx8roHhYSkoPisJaO3pNa8WUMAiOmRuCp7Kw+39UmLqCUE7kxaH78Ec7d+8VDAVS1lPVwDAg\n" +
            "KCIyBRBqkPbpgjXZ+YANvIFwAMsE8CY2RA4R5RdNzp/auvthv3nWm/0sj+05wMG3P2fpBEtDfX3h\n" +
            "cBUpt+xYcUrQ9v9d2EaW5fMHXYtaA4+tqY0bxU7YvTOA01S/+73tfD3XaxqkyifVRz/6aOeLyehG\n" +
            "nuapYwdAa2WOandkJF32ziQp1zANOF1Huq6DizXw5NabozxVak3nN3K9SnwMOKaEPvj+9rubGxBI\n" +
            "00ySuvPOuyrZ3drc0koB4MbsfDEpikKnWZbmOlE0oBCuRyzyfwnrXxAGZJ5aCMqyrKqq3b3d4sty\n" +
            "z8M+te/9xvbGGyOllTW1A9EqGWPhKVvLq6dm5/Odj350N0/T93/wXp6pyhhzzHLgpAQpKh6USun8\n" +
            "tVvYm+hEW2s+/vFdYJyP3jePKwBY0+TcaXu7v6svJe4daBggMk8qd8SjmyOtVH1YwaOa1uWTMksp\n" +
            "zzMiqARSkK0rZktCsQc7B0jnMfnZ/seffMLe3bq9NflZ8fEf3C0Pq9EN9e5/tLX561sApAcv9CeY\n" +
            "G+ESBqbg+hVdwDKEEpxrXPXUuqZFkc2UoHm64lZ3WT3/UqeVZ037ZU4rOgPpwhhmhBPMxTDO/sZp\n" +
            "9aH/uu8mXZAR7ckdgAGxYxLK2to1TNcyzJqxQgoYNiR0uq7T9QwMEBtblGVlGOWTaueLve235a0b\n" +
            "io8YHJo9z13lPPIsCaMbow/eo/y1W+GdEODJr+ejG6Nw66EinWGWgnSWZcOsBbHxxhuj4N4nCT5m\n" +
            "EO58bzz+9XxzvEEh3zZUsGplfKuM9BIN5kfkwPAsB0TEIdj5yY8/2rm3l12n9zfHm+ONLM0cG/PY\n" +
            "wDMSrWWKhMHYf7D/yY8/2d0r8uvqh7/z/uhmbpml0MGFZjzRscyv3QJwMPl08/XRu7/x/s69T/cn\n" +
            "e0SZFsoIznTKjk3TCt95Wo5+a9/sCzUtyRxx8ajgxuTXs2wt3R1KSrKdvYPJg3J0M6dEAZRlearI\n" +
            "PLXwBEFEKQYVhJp8WXz4+x+bun7/B++Vj4qPfnQ3G6rtt0Z6qJXSzIYAR1KTZL9s3zvLfyFiAc8Q\n" +
            "I3CQ8NBrqTGWPduGXfx8eUrvlTpnLFS1WPzKKaBRPOzMfXyxf9cVyHfgaR/5/7lOc3VavMPFOY0T\n" +
            "6hgMJGycg2c9jN41xxwAPJoyANW0UqE0JaCVuvPOrfEorytbFPvcGLyzla1nRHL28E4brmeQHmo9\n" +
            "1CRViJNDxFI57Nk9ZQBBIaSE2LM5irqtXlXGsQSyoQJCyy3SKss2M3PEmoiPrGtYtjVqlywpsUQL\n" +
            "iMLXsVwlgHZ+srOzNxnfHn3w2z9Uw6DSMEIJ2gGcgzuydW127+3d/XzXWNx5Z/z+9+9k13N2HDY6\n" +
            "550U0KQdYJ6aD3/vo3pab29u7k/2q8f19tvbG+ONqqp1AvbMzDJRFzq6llLH/yxgbVU+LCnRWZaT\n" +
            "ACWqKAsuYKzNr+fZMCOCVlprXVWmYwY+4r3J5OBRaab1u9+/k13LPvwfP8xT+uHf+mD8xlgO4JwN\n" +
            "QUrZOjIuT/GWAt5RQAJOoHpqmZmEylKl1xQA19jlYJZL7/zfxBzuX9f1/15+P1sg0Zc87ZT9kgt6\n" +
            "XnC5kKjXWNtAKW08aDWa7sYzAVLG+lPMDMFqqLff3gQy22D//u4nP/4E4N/9L38oCQG0c4WRBT8z\n" +
            "EZx1HjIh19iIdhMxlQAeLCI4XHZGimMpENtCeQBwbENecEwy9QB1ptASzj9NhKDzs3MOx1TZavfL\n" +
            "3TSjD/7mB1mmnGPnQ64OAYxjWIu7P/50d1Kw5zzPfvftzfGvb0gix1YT8QDuiGVwiDAx8NGP7u58\n" +
            "Prnz/TvG2k8+v6sSurO+SbEht6qM1UItH+0lqPuOM9ZYWz2ttNY6AQApMfmyYoF8qHSWh1xjBSii\n" +
            "qrGuYaUkexw8qfafVARsbW7eeXsbAuPbo6Isq8fV6HoeIEoylu6zLoQGFibzPMZro/peAuQAbkCU\n" +
            "ASaU8GSAwS8P3n+aLruhX9mFOadAzN/Qufy/oHhcpuXQYumuU44A5wH0UJPtAKLDOtRmZmitspR2\n" +
            "9/Yra27dvJWmKhvqYAK4Y1aJlgOEXFSi4JghJeTGeGP3ix371PAZ2tTZw2YAEiQ7K0MAnmUyA5aR\n" +
            "IA6qup/30MxWm4so1DZsyYjRdVCoErUw/W1+zhkUymZJIV2LkNn69Q2lVBgtZGxS5hh1XX74D3bL\n" +
            "okhvjLbfGm3cHoXuBhjE7FTnWIJolUL3jJ17uzuf373zve13v3fnYDLZNqPySXn3853i4WRrczy6\n" +
            "fksNFWltqkomV5jGOOx5i89atrUl0s67TGWj6/nu/SIGVp5aYy0RKKFsqCfMxaMDrcg0tp4yBLbf\n" +
            "Gt95ZysbKsO8/c529fuf7HyxA4GtzQ0iMo3VIUGY2qXaoXFwmf7ZMQxZT7k8LKvHu+xRHZaVMQDm\n" +
            "G7fP0QuP6rn512fw/wtIwz01nItUpi7tpxvBXGPM+cMuT6dv0kWUGAE8ev3Wnaf1zhf7dz/b2/mi\n" +
            "yIY6z9XotfzWzZwSUmvkXAiktVqiZ25YJYQExgb9rd2rL0GdghCB8+G7PLeA2g4ZvRnzC/uMJEl8\n" +
            "ZNHxPBiCnGfpAdmesJdnfpbPj2bnjGZCVVU4xmg0CmaIc204U1JdVx/9aLeqzHu//d7G7RElkhI4\n" +
            "zwEOLmUHeQA7wBM3PNkr8uv5u+/cSYdSvTXOR6p+Uu5Piupx+fHvVzrZe+8/fW/jdR2ihpecwzhL\n" +
            "C/+yKR+XpsE4U5IkEbJrGkTOMhpmb0Bh5XA2VGCaTPayTNdT44BbN9I772zn61nw5+fX8/d/487d\n" +
            "z+7e/cMdArbe2iDRdWfuLyVqf5+DBY7iHoKYufyq/PQnO9aCgGpaQZA7inUHX7i7/8ptfERAQy9r\n" +
            "wnV1WnbZoKm2O7DrvYmrViBdduYz/+3enMeNkQAltP3WZn5tXD6q9otJ8VVZPCx394osVRu38/xm\n" +
            "nqdZPhq5o8gDBCAhMIfuUQhgwecdsPPzaGox+2jxRkTrsxEsaZabRBESG7IMALTFAuavtZAeM5sC\n" +
            "tKfxEa5TPzFplmXD3ARofkAKCTLG7n4xqR5Xv/tffJDfGBGBvSUBKUEd0sm3+VeeA9iumpr3vr+d\n" +
            "pgq+0pp0kuWpHL+R29ru3i/ufrZz94u7WaqzoeohJnqOSQ+c5fY7NZ/79/f1kDZeH+Wpch6pIjVE\n" +
            "OeVbr6vR7Zwo7q75Nb311kitZ0qROaqzIW2/s5G/Flr0wXnW/x917xciR5LmCf4eLOEzcIEZhMAd\n" +
            "siAdJJAnEnQkLZgMTg8KqIdSUQct0QuzzRzc9eNxL3f3dLvs0+w+LDt3LzN7T3ULN1QNTKNqmKKz\n" +
            "YQuyHrRkNqjJGFCRXiCBC5TgAUpwAwXYB7KHezDzfxEemZFSVvfcB6WKjHA3NzO3z+z78/u+L1Lp\n" +
            "TkKRKs6K/FUxeTCRBDAsM1DjuDckQbyoKNJSYM62fFuyw+MvHssb9PSbb/JXRTEvd9nWKPKNm12h\n" +
            "i1h9uNlukkrZJPb7kEO/EfivD967HAi8/PPmElH3D9d+YxGQfGqkU1LprWzyYK+cl3men+Qn5ev5\n" +
            "V6/mSs0m99Ipc7qdUqTgWN7Q/LY6/P6geG327sZqpH2UqyZaLkqxyskD/a9Pj24CSbHyAf3dAZ7D\n" +
            "A6aw5b2aSPQ8/ButKkHM4cp5WeWvikefPirflvqGJkF5kT/9h6ez2ezxLx9ndzKvjGiPjX3P2EIX\n" +
            "U+QjeRhgNgDrm1JSnU3YRz1Cxjv68XYKZ09ezMp5mdQG184QLslvRb35IbNgZpOmcZomAOA4iZMn\n" +
            "v3iy+2MxvqfSnRTOJ961yU76+BeKVMIO0wdjfLqf3d8laqG4xev86W8PTo6LbCeePNgD89EPpwBn\n" +
            "dzIdyU7YX6cva1ajNZzcTKp3XDHHo0SpOYlC3qA4TgGk22pvvKeUDnniL1wwV6WBfN7e4nh5+70y\n" +
            "JBJ1rt7myw16eA1GjMFApWtM+98IOUoAQiKScZxkd9L9B+P5fH7y/CTP88Pv8qIos3t7u1mW7qTl\n" +
            "j0dP//Fg9sci3cHj//5zH9jbPYoBLMuBXVvgOmfEugkdNGp0IuHJV8jwvOdzyyLEw15BjBYgpoqN\n" +
            "jlS6k548z59+/bSqqjRNdaSrBR9+d3j4bJbdyaYPHsmIl3ai3tbTH4hSBFjLRm6x5QoLSEFEunKQ\n" +
            "RGqkDcMsysolGmu95Q2tEQTIP2j6YD9JkjT2tQNYK3r0cDzdz9QNBhiuanKQ6ViToGphJvuZUhrg\n" +
            "ULcbkKDTl/nRs1wrevSLSXYrzX88ffpPB4mmZJToSHok58YTS1VdAb14nR//4TD/oSjO/iYZJUd/\n" +
            "LHa3VfWWeRuKNrQdrKWlop2o4f2dngDott/18fnCSEAbRNMPQG7+v0n3nMW1MH/bgw+blNYk1v8S\n" +
            "QBhfXXtLwDrrYy11RDpK0zgZ3x2X83I+nwM4zU//0//5dx7BB4H9v4h/9fhJdie1V3FQXVmvXR0O\n" +
            "AEdd3mPUObmCwZxYXD0ltgPAmhQLjO/t2QUffH/4d//310TQEVWG5wYqounDSW2kHBqHaxi3/vW9\n" +
            "hfdQAHiPkBHEBeOCdZifl0SItxNfVLvb2BKfd6QArkELDXKOAdIqefLZE595ldkEI4KDuuGdFFXr\n" +
            "jnGhi5JISrLvEYIya60u/SR98pf7aZqOf7YHIBnpyTgjkhQRBF1JO5eRKueFUjJ/VXz17e/mxZwi\n" +
            "mDkbUyQKleOv//4rY6aTB+N4E5CY3xzXL7kum7QFfPps30Nody7vqfo9fpGbcn7H4H8NzH8tyb+X\n" +
            "IAbkq7UskZtLkMcjWA67piSinWQ328V7VJVlPlaK9sbJeH88/YsJBLDwkCG2TRDbINV4Gw6hmldz\n" +
            "CgJr5nLJHICOUUNceOM6EgzmZESffzFNbyezF3k5L9khFSjfVOXZnJ0FuFutkJp/lgYvAMekNYBy\n" +
            "XhIpdvPKWG8ZrRwglBSyODPMTWb0gWm5WmZbQkg0BgDsvXPyPYJ/bssv8JAxObhd3rNkG/ayWmXd\n" +
            "u5ft3kr9MsAWpTtZPEr8jZZZXiae9GgBRcnsn48Ovz/UAp//T49B5Ku/MFNR5EffHx09O0mUUvdS\n" +
            "GV3G/xc6RIeZpWMq7jLCqsWt/5Rew0NfXkJ/Qt/lOrp8r+p2k73Y0+R59wnqqncVGDrS2a04y3af\n" +
            "/HKqEmKLkNW3Nch347FDuvgmTKLxyXWv2YDqQ67L54Oj+FhidkyCEHAsnN7Jdu+Nq3fG2wIOvz84\n" +
            "+PYQzCRIihDM2+X8wZ6n22lyM8lf5Cf3Zrt3U+mnCz6QVh/98eg0L/bupRQRDwMlloGJ9Z8cfusM\n" +
            "nIQPq+DgKHUWpPUWuJNoPFTy9NkNmTnUApMej0iC6o2j9Yxay3CQERHIZ1K0bhiMuI6MMUfPjknI\n" +
            "6WfT9FZGAj6DmDnneKRJ0OHvD05/LHzO6A+mQc63rj3zbP1PkPABQLZndVCoJdCkNu0IB1dfYB/P\n" +
            "/H86WCBQAd6s7RE4gLMQEkTkgAjp7UTf1PnLopyXFCXsrKIwicHZ1slO2RaK+DgTbk2dFXmZP/mj\n" +
            "HsMMIusMsyUhsUUQIMEsAszJhy2FPHy9+l+1raHJBeCFdsLezyeH3xVff30wfZhltzMSBJHAoTjL\n" +
            "n/7joRQ03p/SOndJOJQGwfQDnScibrHMYMfkarugZ1pBeK/8opekJEAwbTC//+As+32/hk75nnhr\n" +
            "bu1Y3ZSsw8mLU35nJw8n2a00FP8NpkfSEU3Ge8WrwpwzL4yNJNabtLrsPXTNILPYDqt3Dvk1y9IO\n" +
            "Ipev4CxsdxOxBk201Mu+CfHC+EFvwFyqRnqJFdCh3fCC7D0YkqUBy4vQcelr9Ti/aBiAiii7lRx+\n" +
            "f/LN74/GP99LP4k5AlxFSiqhfCkoXtRLX0hfLtansG3UV4o0RFdwC2kYCSSxnBsj9G1gXCvRr0uu\n" +
            "QQFynUNsk1oDrj5QXSVFyOJgLZNQAMgx2JILiYFDJ3pmhSFZVEAC03EGN/3dd7Mvf3OgohMinWjF\n" +
            "jLKcA/bxZ9O9Oykck4Dt91DiaiKSBdNWPRaEhVTjJdqWrQ98dv4t1w4O0UmkFVZgQE+068qtSGH1\n" +
            "MNuHLn0JQKD4MacbOk0zgKgF/IEUsWGA1IiM6UzgalMXbzcr0Xtr8/ld8M2F7W985ZLO33l/wdLQ\n" +
            "cbYtBxWI4TCDK66D/uOucIfuA2nCh2AFJYzvpZUpZ7k5zQ9lhDiJ4Yzfv4lgFmy55qL2GKlfqiBJ\n" +
            "UCrxiJ7A1aQ0EUWSBO3drAUH0WbgIPLBJ6iHImUnzyzDkpMQNRwIYGfJl4KrSwwHaABCl+ptqAX/\n" +
            "+GsYgCBEHN5Jc5dTIUTHoGJooovO4Z5tmQAQMwns70/S29nh8ezoRTH7MY+VSrfTvXvpeDzO7mhJ\n" +
            "CjCtgbi31GjgjfjvV54oBTVZVdpvHQCyYFmnx5XdGx1DkK2f2z0hG1Ro61noeFgup7q3xfmcAR0n\n" +
            "RFqCLIAFSyLrmBeGIirOi/l8Hu+kKlJrzr2g5tQ9p7rUxxJTyIG6nR2MnATQz8zjv2n+bNL+fizO\n" +
            "RyzzqwV6D7ZD4UQS/b5ekGzowm8+llYQMp5X01vpE6V273HxpirP8sPjGS9YqZrxOCwUIkBARmhh\n" +
            "Ww7VAnBg5+vtwgT+93I1APrS5/8V0p9Ivg8kCJJUJHWkSBGRlILseelXrRRQkdY3lVKaiGRERDEF\n" +
            "mcWHx1tsGWo02CYht1/AvsS1C9nvaJ5QpIgAEUMwiCQkHFkBXrBZgBl2K2GnALPpnBPYsSalowRK\n" +
            "UkRANb0/mdyfxCMlhReAGQRZS1h9O3MLrOzTsKAx9CXV+QjX8ewlvPxR3hkgiZQeabCtFqWKUl/e\n" +
            "kx20is35vHhVHD0/wnu7l6UU6Q2fVmvvUrogw8tVl9ZH0DWAe1feRhcULTt/SwgL125Cw/tfPbB1\n" +
            "PbtwOV7FarDajkdoOkgQRnpfxft/oefz4mT2b5NtPPnF4+q8NIYpIiJNBEmgLZAiEh5mG0KDmNnn\n" +
            "smdTGWa7YLPg6m1ljKkWPDtr0tq2Fdob1BCFIlAMAXJoq/QKaCIi0pFUSmmt1UgFngFTxDKq6mIh\n" +
            "YRKYbXU+Z6/cOrbstVDS71J1g0hJSaQU6ZuJUkqrREW6nJdVZfQo1kqBJAY1lCFiB3IWjjxM0Eew\n" +
            "ZFmWbid2UZULowgyImz5hD8ryDlvg1xtdsPH+2tFR1BvreXUtNSTBTah4V1m+NpkRIpolp+mL5M0\n" +
            "TjWRBeAYW8hfnD79zUFp+MkXk3QnvRTjcEmXrmSJuOA3d+XWVlqwEFKskdlWZ2oobcDq8ftxdOU8\n" +
            "B3XwnBTUGKKlI+sMG1PNcwmejNV+RnAJRNdLGzR/6wwbn1qPpKJWiBUhaZ91zAsf1G25MrXoHlQG\n" +
            "v0gtV2bBvmBAtQAzCgEWIBH+5AUzo1jAvvbHeAf6tjxpJBuo+TKxxAz9ZaEJybZK07SsTPGqGI8z\n" +
            "EGPLbg4i8L52dpUVUDcSFYGZzWJeLRKAVUSSYMF4DwhJS07mrg8F7U99YF/vp2FqIAm1ex/wu2eP\n" +
            "1m4oq7vP5sgOB17M97Ikf4GvfnNQGh6PxzKS1cLOvj08enZYznn6IJ3cHyuxGovVeVZPkeElcb3R\n" +
            "+a/NMP7xEkQvnn+AbOcd9+FEzkK0ZdUD9ZA5V6APVwpcK34DLc9IFUuHgisSSOKUmb2C3RSuqlEo\n" +
            "FoCOgh3bZ8vwKijAHRMgxREBSu4k/cfXQX5NifW6J6eOWGi/p8AxM7NDZSpTGebKctDtmRlMcD4f\n" +
            "cegPef8FrSDUBMAMVzFz+ed04cQAACAASURBVK40c1MxM6M8N9ViRpHKbqV7PxurSLO9SvypA5Fk\n" +
            "AGzZGgB0Az7RKAmSQJA7hCWAN3i3FESj4WcNUNe82tkQN2XfdWfgumf1f/IvLruTTR9UX/324Ku/\n" +
            "P3j62wO9rdhwaRgO41tq+mCabgc88lquc42ZsLOR9d7CFdn/mhSEQfJdXLtGlgDGsvaN2cbx6ADR\n" +
            "OMzk4JiuAep7gXiz/myxC8MOZVnwArEGEYOrYCKiEAAiUR84XHo21gIQEiKggKUfWrPxOUBUvWeJ\n" +
            "9t+u5VICu4KAeV1NBYjAjkmRvCN9WkH4DIhBIazn00u8ArJBaAsJZ5sXwWIXUAxmTiwzM5htUfDR\n" +
            "cT7Zz8b7e3qk1A1AGL+NbDLB1aLy258kGeSAt6Z4WaTbKSllvQGSyEMJOgEXa1cxD0s066mV8zt9\n" +
            "XqnXtNntXWq1hpUvO997h7HD5MFEfZIcP89PXhQsoGIaP0hnx4c6SZNRvP4pw9SAc8KR0JyUA4LD\n" +
            "5q1eM4l1z+65wa9EA0bOlcYvcYeutNalQT2l47YJBlvmfG5UDB0n/bssA7YDGe6biG3brOhzPmCb\n" +
            "yNxV8ic8QIAV3g4X7pLwBkJpnbWLHhJLkoSztlPeF4DsGf9t6IEDBNiVAEgwEVREBIKImedgJEqH\n" +
            "0wlga7Xrq2ndQfWnVEYeQl8BQERaKxLq8NkpHI3vZVpRBUiSFjCmUj67iVegBBRpdON8az/FQLxK\n" +
            "M8HA0q7kQwyXVVzRn+qOldtTz9Zd779dPFy3D3JpzSw9jiQDFkh30mQnm37hpSEqzooiP0liTZei\n" +
            "+kLj68Cjaz7/pCCZNYU6uiRa4WRpww4zGNaQdW0n5cZpjFdd/R9OAgFbulbu6v1VLqr8ZZXdVvDp\n" +
            "MZ1HiXViJMWG+5pt7B0cZqM//HYJNl5raPaqr+wuUFp9y8HH3UUHkr+Mu9sQgsFfR6W/x8LPBsFJ\n" +
            "JSwRW65kWHvkDcsrZ9waYrBgKUKa42R7d7Jvjo5PDn5/ePjsRCkNQQZgsF2wFFCaQuoEgWSktVLw\n" +
            "BZSC1VNqTSpSAU/hljZrRqvS1GgZCpf5bOLeeVqn4iIC6nq7fuAc3LoIG9mA17lzEnQiMuFDFYID\n" +
            "e8lM4CFSIAiiCCSkMdXBtwfkkKUpCUBwqBy1mX2h50m74sq/uv97A+oZIKwfw3qDX+iBrRX7dZuU\n" +
            "HPz2UrqyLlAvnU2uZYfiZcHMyXZGhPBiOQjhEvBpnr3RfrXzAz5OZwHoxcqla14SQ1qB7sbqT+/h\n" +
            "N9q3lbDoTnVf/qrtEUG6cRIgsiQZMBKsav2ZlqN6LjQOs6Om/XikHn06Hd/L8rzIX5XMfPqmLM7m\n" +
            "HPXsIE2zFEZX78uCiBC4mzuRP13RTIBCBQ7QFoUCAQSKFJEk/1/jYd2SjfVD1ihFEtTYU1opSZA/\n" +
            "qyjAFwIUGgALBjw0uFNrzJtdQqrvYMS1bs4Ly8z5i/zoeT65q9KdGL60wQcjM6+qB/nBip4g86GP\n" +
            "7vH80jeXseCKeXOJVX5SWO9VTQayCV9xyF8XAKdpCoAdq3b11/KwsPVWsqKMDRp1ATX0GgaXRAUL\n" +
            "0RG8fcnwdWdwY7AcwEp15FxHNTiXOAyWAGJnrIMxFg4ktH/HLHjtTrm0EfijLFSeZDhQRLu3snQ7\n" +
            "nX5K5tz87W+fGjY0UiDpxSh4XE2oLxBy2zbxthaQDjGBqS18XPsyAQfrBXK2AeDI3NTwAwDXyX0W\n" +
            "mLwxiNR7TUdZ68lWBBKSpL+GSCCU2hIef1G/CMfMNrh2HSpmAESQoOod88JWC04IiUKW7RJJBIOI\n" +
            "XbcwNqKrO+dWHZxXEJ9X2X6gR1fp0yWsfiU35qo+tiEN+heXHu2YjYlHSEYarqJemIeFk7ZlyzUt\n" +
            "LD9UAjBXmHrbzbUQgjE3GOkS1rrThIRXXoJQIlGDbUkqKXxpbQPR27l6/N99uujxTKgjJEhK39FQ\n" +
            "UNCnGvP6EWkJQYh6yFklwFAU8pFBhYRCCMUIweTgnZfdJ9axSeHQ1vWG4gWxLo6o3Sx96n5mHxdA\n" +
            "NQC8tTc4WGY2neEP7rT1PtKLRCQAUATpoIj2bqfjeyod6TRNk1iRQI0Ab9/LRvRxeu4yzu8aqYb3\n" +
            "r1mPAhLSOtuRSJc70C7TValmqNUP0P+vkvCo81wHNhWz3U3TsLK9vi0Q0rm7GrC0nhuXYhPqK4eP\n" +
            "0lW/mm09BR9Gne2pN16/2mtkuwBAeiTjJGbH7KxEXNcgbp/etXq33NJ9a3UYHEkiyWxBRMywHAA8\n" +
            "7P1YRNygG/yNjbXP/+NCSgxTn7gASLB15DWvoNsLSKLWhdFsChSEegC84K5gZgEiCoG6jpdPjlBe\n" +
            "jeE6qYq2fKkP3wcJgBtZo5VHCHWWdHLM5ybdTqafTffvjsEmuZmUbwv/FGssqBUtLyXbV5M/hHs7\n" +
            "7/1jM294KaBZ/ELW8N5hc7q0tT+va/bvFAkbIHvZID/K5tcY/IYfzd0XU85LvOP4Zyk5q71GxxXg\n" +
            "0wQ002rrc0CinzyzAY03zYee07D4NGBzYgRIv2/Ytbw3PLQNiNHqqxZespAAIWKM2JRc8ozIyC3Y\n" +
            "94BLpKNuy+tVAAZZCQCW/QqHZedlXcVgs5jbwOCdMmcCEGD0EezUwnvauL3mH2/FBEhI9vkaHACY\n" +
            "FmEdzAptIw6ANSLoZcZRM5OBHGz9KG/KQSNlWA4O6MYeeWO4fHCANr4zlWPjQACbOQmUb07ZMUVk\n" +
            "nWQBEtJrNJuRHPi0hi7niw/g/FVrfxPP5+wqtr+5bW1vrYcr97/rdL0ODXADh/bSCC+W9pcPXtRi\n" +
            "oegcYytT5pO3WsdlOYdAmmgAvsYeFmj2eNviyVqO+Eh8+Gp/uPm/W/3yA8miA0AIXBfatwt/thIA\n" +
            "udVFH2/QbSEhgfd+WYCE9AEF1nHxqijelFoRCMwVevnwuxtmgC1yM8iobrz7oKHP7TsIRQ0sI8Ax\n" +
            "hu5iQl0rAUDDjWG11Auje5ffHrY6T2xqooSgIGLHYGvYmx4IXspgH/0hrQMJyWzXIhdX6Jq1g5od\n" +
            "hg+fpeScazT81VsEVtHagXpmMNkEEgtru0PrTf1F9BPG9tQ4PzaQEczCFmdlejvTCiQMUMEBpP17\n" +
            "taJsJN4B3MWwYXYYwtQ8vU8rAv81DTzIfs4CLB0RNIsCgEEiBcAJOIXT2AqZuTYlQRAEW+8cwaFA\n" +
            "IDJAuQCD4zgGmEhy7TOrBXgvSXFr2GsioNusG0AtCCjS7LN5iNYUj05KbAK440sjcK2d9Tb9QeUl\n" +
            "yHTvga1mzi0AbAX7iX0PAHKrTsLr7xIIG7QACSqNrQyzIwuWguCsrc9JEpdF9Vxm1V91mEnArlse\n" +
            "/XYuFauH+nPJHWtBPm0PVk/gn2BlL9GVdgofhiERnEZsDJgVgUQwSrdjrF1lnT+HhvBhgwp3taCg\n" +
            "j6LN+yBAouPBen8dT4cvd0vzt2Wt5FsI2Q1/9jsF1VVGATSRESFRT2uWIwJxv0Ajg8NOsU48ceEn\n" +
            "f95Sw94CqN+1J8/APTmk8ex0SKK7LyAwo5NtLh0BOK7TCq12aribLUu7lW9WR9TBR7Y4pY9koiUm\n" +
            "X8/zSwPY4LEOEGvkDeAndva15Ov2rptQ1MB+61AtmIOnjTpSd1fnbcBC9NGS+E9Ggwy85nWRoDbk\n" +
            "5rrgIQ7suHhdWAfl2Jv92QFCkmeSTj89JofA7Ooj2rUWgY7+z0AwuxIkN0fu8EhXvq5TMLaeeYSn\n" +
            "tOBBb9Z9D2zJ3pYB2Ur+QwoICUnEYDam6mZ8+rPRVV2Dl8kd6+64jLqn/YdOyjqrxtXUgTWOVgmy\n" +
            "3YPFgp2FU+GWrprXtBNoiP/XzeC6rg5b5n9K6iosDhCSG5u8s1deN0PEjkvDxZsSAvChvgh2vk43\n" +
            "ANRyAQDIxqvAbki8cnULjamfbQP4WSYvRNSuQXhPpwfquBUQQ7DIAqAazVJ7YZa2RSdrKWk5LM0X\n" +
            "+ayM4XAwoL+TriwVt9rK8DfLD6ovs5u/KbdeQeh05mJa2k035jy/d7aq1J/owO/SukcuQSmZbcWc\n" +
            "LLgBb3WoD6G/lpN/mfOXXPU/HdVjEbUtoFFwtq5F76ByXlTGSCLakiRAtJRGqc/eHbpoThuYc63w\n" +
            "h+XU3SNqmABaUAA6mc6411rIJsIM9DILNCrDKrn+Ltb0nwgwpansooKP9awZ9RJpapWNhuZ/IMnN\n" +
            "ep3/altD79F9+996EqugvwvJtktcoIFnX0v27otpOU/bgC8A/l3zOzbGJ8BgBmSQCHor9Tr5sjf2\n" +
            "60Nkb7QtE8AkiASxs2y7uu4HglGDQ8QBQF4UZsGkFAt4wxv1CooTi+Z1SOrY/1qxeUlSo54hkGr9\n" +
            "q+N/rc00TX/8Ib+Enxf1lf6NCybujHfDxdzZrO37sPtYhqnMfGESyH4CiHW3D/35E6kMV+Wyy8z+\n" +
            "dTdrk+aFtBxq4l3N6xISN/QBdv4BV99F3YJHpEHAMlfG8IKZud6hvPrqUXHddFF1Iq2ugcZh4Oi+\n" +
            "xCtp0Rvjx28sNshZm5B35gfUqkVb8njTOIhASxKyY2aUZwUYbdSjYzA3Gjtqv319aEuurfGNlQ61\n" +
            "hh9uqaEBIfUwtbJ9Kwh4WK4jbrbsul5rLckTmtjN2tHQdTp48yfVMgJ5i6DzulJYEuGtvW9etm3E\n" +
            "h4qtXXAvBiwQL89S+xYALFv+bL3xtQjtjnTTBo+s0WSvObCnpkZjIvHBe1TPZPpnUAG61Myj9cl0\n" +
            "nEUA84WquACkY4RIMq45hAGJDopxraq8ziTrhYjBTXN10x3UM1cvcHagDxd4gwTgIL2te2HYWU3S\n" +
            "vrPSpy34YImMqDovvZGPpOSGnx2sZblVZwhm2PfQN+pBNQ4/104mdeeH6wtIAkvV03oUPHA9qz61\n" +
            "2TJq62/VJl/p2BGC/t+CHep/rX3vV4uFBQSwBQlp39feWSIiYmMaaKCnHqM2tKI1dF/t4Im4dJ4F\n" +
            "heJiFhyWcDejFbd/L4Zi7bJaWrur67vebjeBPfhjfA2896P3DtH4I3xQik5inzJP+vwT2iPS64Tf\n" +
            "3ThwEuCl+D2BtRinP5ExT/aDAupHXzDJ9cELQSSkdVbekPT+imL/ihDLC+b3bAG8s3RDkwgBcHKr\n" +
            "DpVzoGD59x3o3C4AvmzPqqkTVNfx5q8qd0t5crzIQD6xNxOIa6+Bz8jeKVkU7IDsZEBANCWShaTw\n" +
            "+iUcCNaKYA5YQmTK8NemFqLlApa40Kv3p7ITt9iK9X1ZRzIIya1wGGykS/QnMAEsPS7I/K6u0yR8\n" +
            "fKiGYwiuo98BAEEmBOBTeVkbXtUFwvYSN159t7rAVzKwJgZFie5lPcZmeBt4BeZQr+L9kD38asRm\n" +
            "wbywjXbNBCyCZwGw7OOj6TJQQ1eY6tv52y99C645BrpRukBzkvutpxPoan3AQL1NEGitKFE/V27V\n" +
            "NrxQOtEyEHBuAtxUSd8abuYa6E/LGgPUL9px2aXtZ/+/9qj04ZabsPr6a4YZaSioBptg+62DZVZE\n" +
            "MtIQCs5YJvbCZ11/svUAhah+2wkUWQVorFe+1mmAWJGV2iW+pqmPWRM+oI25zrFBliv0U8Rfldih\n" +
            "eluV5wYAka7Rvt4a108x0Hzon8wMS5B1Wa62q6udbz2FHW6noLTXh60P2qmxTCQIS3bc2uEPUSsa\n" +
            "TV1GF8QKoHkvMrz0VbO/A/voIBGAgPJiZXANDcTkfZjp/tppoFDnqmA/wPZdF8KG9oguL/XjnC6x\n" +
            "oq2kXgLWGrGEX2shA3RyUyGi4qwszpDGsSSA2ZfN9Wpw3QohJJxqx1I/1/b73NDqnKxeM3T9T/zW\n" +
            "8x8LRUmappVjuoqt9ALiGthDmnhR18xDq2e11Qc8tTWz/J8AQizfRmpL7dL3p3ctdfe8epccVQ4+\n" +
            "/REJyQJ1oeAuWS8WhYP9Am4MF1Dv5YY6C70+t31bHVTfvIcPdt1dH3VQ2PCpu4fO3qUvl4e6Fr/Y\n" +
            "p5/IYLlMksgy+03aAsl2mn6yO3tRHD07xX1QTIDySFKfoxJgoLIOkmFrtWVFNrms8xe/xY4bqbMy\n" +
            "1s1YN3HgxXrHwK9FUeYveXwvS7ZTwLAvZd2XkrqQuA1JKa0U2QWDwe9RWQtASwxgctY022Qi66Uz\n" +
            "Q59VugMX6EODPPsSUIsVAk01BOvAoZyBBUnypvimALlbM2NNRvOtoPnLLfSOQgEiUpFeMtRfQF0r\n" +
            "0v+fyK0zNS55/pYkve7KxmX7cUu9mfygZB4Dx75lK7Vn7CB2KqWf/OIx6Oj4u29ms6NYQEaIFaSE\n" +
            "UpTEpBKdxIpIIyJZC5YSvfN/pW8DisDVh7BmQ3FL16y3OA49KH+VM6u9n4194bBaTP0onZ8E6ZtK\n" +
            "j3Q+NyhLdmzYQIBGyltS0aTv8devNiEG/gwevhqZE1yDFHaHNrC/K/SJ9kt2oTxx6wvsx6iuTVje\n" +
            "rNvmzEcw/rWsuwU4ScL4wkoA4Gq/aRB21hj81rjTu26giwx+f8qNo366n2oxvCKD6Nv5c8lyE2g4\n" +
            "aHlZVh4+WuuL3fKVWMtO3tHaydzmp/gdSxGkMkkAGIQnX+zt3paH3x+e5gWfgV+DFwiOX5onscpu\n" +
            "Z+k2JTHFNyEjIqGoNm6Ftey8UYtl4yMw/gcCkSRowTXybOmEr+NZhKzj5Nb6bBgWgn2cDDPAIGFZ\n" +
            "AGyIiMP8kBSJFAziagEWksm79yflWXmUz1SsktsMYSSIBDHz8q7e8JUEv2ssZABQMembGrDsatiN\n" +
            "kJaR3qJHDx6VP35Z/jDn2ltujJE7pG4SIsWOq6oCoG/I1v4nyALMXvuQ9Rjrp0c6pAMJdX68RaBJ\n" +
            "iN5URvMz5vk5HO+GPVJAoV67fjuoB1in+mw8iP4YF7a9RoSYHY+DkDKYvhWUqYzys7TgZFvH0OQU\n" +
            "Ac3ZAFhvG4aTy0tdDAR92lW+8qJEh4+uWTBe3YNWvmn3aOH/WaePLclpa/enoAL8idz9rjHpX0Ts\n" +
            "QET7Px9nd7JQsH1h5+emqipzPi/nVVmWJ/nsZDYDIAkEJgIRtILS0JFSI5KRjG8oGZHPDAcBHXmB\n" +
            "kwG2HEKJZNTYn5tFzlLUWbRFLdJ7mxnqtVIPwdY3WtdNNcEg4pDwk4LxSZBl9ug9drCG54uiLKri\n" +
            "FR59JpXS3tTPCwaRFJ10N02rAmxD0h7LFgJEWt9Uh8dHZZGXpjKmZMAyp9vZ3v3J5MFE3VT//j/8\n" +
            "TWnYFy/lBSpjrAMIUkp9UxPAC24U6cC6RFjUG2Iz/MYmh/ozvKDeOmIaUAA7C7YkwGELkCSIGW3S\n" +
            "AgESRBG1rO7QneqQINh1eYB0JEP5Q6DNCPTOQACS6D1TRFrTR8hMl9FPxPkfRAK4zB6zEV3E+1eC\n" +
            "6230MNeTJlYbDxqggIqAiKQgOLV7K7ECYJiFAaNiy8y84LJgs6jK85IrU51zObfMYMcEVlElI1KR\n" +
            "1jehIq1HM9KkFMmoTiwJGOeliZ58FE5CT93TDBLhxYcLGPCnGdWJFbzbmWG9hAFH83lZLSwDeBew\n" +
            "MeW8Ks7K/M28eI3sjpo8mFKdUcui0pDsVnJO9RwoVCcCwtHz46++fsrOMKMySEZgRpEfHx/Pxven\n" +
            "008fTT979NU/fMNe6GWYMwYZCFZKUQkAOiICaAEW8EnEZL0L1I738N6wpPz7A/y9bkyw5NoYHvb7\n" +
            "w/vaiCiALSZwo7fzVs/nD1cnMnAgBsL+G2y94ZYgOfp+ai28sFCC2ZwDzGAmAGzhCN1Iges72v4l\n" +
            "sL2nekWs8n+zZ2+s0PokH8MGqysqNh9Z6kcGH2QwNodkPs4jT0gpJYFEKAAA2Z9rj2b1VTF5YSxb\n" +
            "szD8jitTmfOyPDenRQFXeNQABEsC3QhZ6pJYKdIyAkkVtgBoqnP0+wzTXQM4UDuluz0O7iivxxq/\n" +
            "Pry4W5TlbJaX5x6aCHY+Xx2kUipOpw9p+nA8vpWBGURwLIXkoAO34nb36T5FjhSSBJXz8uj7I2OM\n" +
            "UgBBj0C+cnEEOMyOj4qiKIwPxO3MoWMAhg0JEFD2lG0KMXMCEG2x0BoMZrASvU+OasMB9ZBgYWaC\n" +
            "qy/g/0V78rfU96QGx57gYJLorL1O0mEvLxD50AABAjRRshNnd1IdSRLoTOB1cWyDHPzzgGKXZr4z\n" +
            "hYP83/18sbezK8euT2dwJRoUGZqcjRc8wDobMlJF/prmvEUD761ngiAkC4vI+5Z1OChEu+CYuTIl\n" +
            "GEUBc27K89K8q6q3xiyY2c4c+/UPqgLiTRBRBQEJSRIU0aqxvQlKCWNhsAMz+B1bxx2/NxuD8gws\n" +
            "SBHt7mh1U2ml1UglSaJGSo+0IpLkC5ayZat94T1nqe84aNzgdZwMMZC/KoqyJEWmTl9tFiCACD59\n" +
            "XTkvZ69MM+FdGR6M7G66d39MxsAHU75jMAwYDpWrukOu2VV2ht+02Qm1bN5g82edCdQ7/yEDukRH\n" +
            "FNJyukbmtyHXoGN+x8VZtWyIrNUKn0GUiOgGQUg9shRJRZREpLXOtlOfsGC5sMfHU8ssH8ogS2F4\n" +
            "GwTwrbXILrP7BfL/pUe3QyNJ1nQNdoC1KsOF/WkTni3Ywh8CJAWsaKto1cQQRT01NqAYJfAedbZJ\n" +
            "UCTViACZ3CK4hF0KB+aqWhhe2KKcY2Erbox/HmqWegWV33NVhVAWb+iqT6fGAc4gKEEUkRSaNClS\n" +
            "EIqItJIUEQSBwdCKaDclpRLvjoIgbzZjZ2pbdAApNRpEM8Z2y6/53wJgLorSGIO2uEW9TMIJyVoR\n" +
            "ReCF3w/6J4dDkqhf/+IJOQuf/c4xu4CzNqj8NQ2evGufa2tyIJzYYY2K/mexvG/WAB7IJoiwcWSG\n" +
            "PsA6y8zlecnvw3gJIZmXpPBcP4FEUgoCMQRRXWtcE1nTyCyb4nmvRq6xqf+0IgB3PrS255pWGMv1\n" +
            "9/g1V62n6+f/ti237Di4SC9wkID1qO6a07qWtqBgC4utNlqLrYEA3oN8SIn3Bvs53GKSBAcKOifF\n" +
            "iAFkLg2I1FZLIriEGa03y8FyffDWRoHaUsVETESSFEEyLJFsDsYakkhwmp1VguUNsu+4JzU4SEHV\n" +
            "wkgBIs1ea4XFarm+moFJkrXMbKsFMyOOafr4sSRpnU3vpHxuytcFLGY/lvnLXCmaLzjcGJFPnu+t\n" +
            "4OXLojJlOkoouMQItQmTa+8JusZ5NGbRbsSO6V6D/mEVDnzfuEPQ2Juxd6mtuUgA0h3t0bvef7G8\n" +
            "PNC8r4qdrC2Ivm7ywLXNVF8bXYOh7WOp0WwaIdg7S/znNbQsvTQtraOm1PdqgFDjJLuOPWLl3UhX\n" +
            "O+ew8ojA8BLvWjQfgZokOT5mFY0I6gBHFr2R1vEe4SxqiRhRL4JqxeohUee0YFh9I7FvjbyhrCUp\n" +
            "UC0qH0iT3Ezgy8hwRZHXogmCg+JuIbfAC0CSz5XPIOsYgugGeZceCW8NIG/G96JsVRnPKsaYaoFx\n" +
            "mqZ3dvPXRXZ/okeab1TFOU/uphCnRVFIwf7QTm+l/+b/+N/YmC//37+d/TgHwDAQVbupNWH8/cxc\n" +
            "1P0QJqQWfACgguj+1FxPQCdM33kFoQs/wSCFqfEhwOg8rkuu+0E2lgVC2J5kzRm2f8vQMvWavOxg\n" +
            "QjczETh8oIK8BllwgYS/amoBsFSxh3r/wwabU8u33WGsgoUvuD1c32WewSP9MhRA79qVJw7PiRTc\n" +
            "Io5XrxRdeYEwkERwTb4HXzznshEh7DiyfFOoSJ88nxFReivRkQZYSl2WZXnO6a00vZ1WpgRQ2Urf\n" +
            "0NYyM5JP0vJNobUq3xTJKAFgF0bruHhTEFhHRKQBWFfBn/bvAAGSBEn2HTNksh2nVZkoXZyV//P/\n" +
            "8m8OvjuAo7I0jx5O50Uht8ALrubMDvGd9K//w18//nQ6e3WC6Nea5Fe/+erkWaGVDgzTvB1caDxa\n" +
            "RwPX8Jrvr4OWmu27RVftSgPc1u4FvSXgv7GD/B+Ypf7pA+SI1eTcF6brDtLTch+aj6572cAVga5m\n" +
            "+R8iv891DnkpYF37GR8I+1v3uHpEl8hX1DlnlnoLoHYyheGbleuGibsd6D6se0G9U7ODojS5mR68\n" +
            "PCle5f/23/3b6m0FATCRSNgUf/d/fVUtIAWmnyXZ3czaCluyfF0wVwBBKzVKQpgKqcpymu7CVRBU\n" +
            "vTNwDNKWYRmSqFowFsExqYgokmz44PeH01HMjL0HvypNkT3IpGC8rZ4eHJLgROFXn04nnz6OI+TP\n" +
            "f1fN52mkk08o21bjX2bpTbJvTVcG9MKRHlrZawRqPTBXw+uNr3Ko1q6+y2gNoNouBXcP4XNs3YK0\n" +
            "aK1xP6EeP4TkaTSmltU7OtTK9e3HYHS+SGC4Blq7ezZM/ieOAh4i7n0aVPBCDczlr2l1gE4PPKFv\n" +
            "bggTLxjA/LwEQSfaGOII5k2plAZx8UN+8uKUF0ZS7ItZJTqBQ/Gm+Prvf0cjmY4UO6SfpPv/3XR+\n" +
            "bk6eH7GDigiOkyRhZ5M4SdO0qgyzlTrh8yr5JK7ezgFKbiqlFEWkHJ/+kB98+w2ySbadmkU1Oz46\n" +
            "/M1XAFeMao7J/tguTJpkGpymKY108eMJG/7VXz0p3xoFrL7in8ZQtjG1U70Gk/sR1ON8B4hg2bIi\n" +
            "uLr/5XjyLybhLSLXzfYDEY0XXf2Tcv6qQ7hHgytjCa5bU9fRNbit9tbcmtOs92iSwszN6eGz4+mn\n" +
            "U6UsKZw+/11eFNOHE9qi8u2pWeSkiCJO4qR8aU5Vme6k5JRScm+8j1qBZ2YIUKTKs0KpdG5M/vyk\n" +
            "eFlM9idqlBRnZfGmyBY8PyuTJMl/LABAZA1Qepbn+X/8T/Tgyd54XJ2b+fFh+aLIItICrHD03UF2\n" +
            "fwpfuo9R/nORHx8nRIlQ1pmAaAB4QNvahIbmSnz8edQchJc2csnuMKjntx8dAGtr4KZ1XUvWvzzq\n" +
            "mPODWaM3PYN2/o8fSUe993Q9ybwvelz3pa49AXhgvLJ1+wHwQiDqCj+eOne1qaBbUygv55yEt+0t\n" +
            "bwHW0clsDhErlZVneWUg1a45L8ozlhFmP87z3KiRMi8L3NNprJJR6kFE6c7YvgPARKp6awBYtsVZ\n" +
            "YZillqd/OE1GSfJJORalyQAAIABJREFUknySsMPR8cl8XpjzqpwbFcmyLLVWRCRvKE1UnTMpxcxs\n" +
            "+Oj5DA4EopEuz0vpkN1Sk/uT4k1x/E/fpDdiNdKz2dHpvJg+mFhmOTixl73BlbNx4NV4BP5qTHU3\n" +
            "SKa1L7h+sy7oaOGaZXG932aAXS9/3VuHHQ+xBGxzwC9JiH8OAbb1qvg/ceH8L4n9vb9cv5UPc0gM\n" +
            "n40bqULXBwT2gPmmzTUL1HuPGgpSHC/3NrC9GnjOcIfLgfOmmZbOLey0vpllWVq85jyvJKVpku3v\n" +
            "QyqCA1Ga3o7j7bR4NZ88fFy9zAFtzu3JLDeGSXC1qKYPp998+83ePjOzMYYdHx0f6SihSJtFSaSI\n" +
            "yCy4fMvpjgTms/zUeoib4/HPdu1bk/+QG0fxrfSYoShhWE0Ex+Q4uaEnP88ePdjP3xSnz/OTPxTp\n" +
            "rZgcJIMNw4GIlqdWrMzqAHHv86YvnYYsKVQ/tG4zAPg6jqRgUVoXVTm8Mvuxur6yMyQa2b6+qy6/\n" +
            "01TmlWKNwe/j6YoGv0saC9wuAID6+1Yd/1B7bgbF4MBCHTmn6cfSFrBuU1y1u14D/zcR8nLtoxn1\n" +
            "mqiv63qhms4EWrGbDkgWXVpXARHWWSm0DcFtlrka300Bmv3xpJznj794RIL1iFREcMh2FDPRDcmL\n" +
            "Ko1BCwAlCSQJSZnYqlKjNKTK9lCZRZXd2SWio+N8sj+ZzyuKtFkwM9I0Lc5KFSk4mHMzGe9Jqfgd\n" +
            "Tx9MJ/fGX7FkoHxprVZ4Bw1kd8fT/SwbkSYmGBVLHTEpShwIpCJtZqc5Ye/u2BKkAzmQj1AisKvP\n" +
            "W0EAW2YQyaVNorNeJWCdz+1LYSp9SGW9hML6RAVnPOpeQtvaYEoeNLUchx76QMICkp2V3aXVyUBl\n" +
            "m7fX1G6/jJaF1s1O2kuoz9LsbA+h4OrhLufasF2IBJaApOjIJitH8nCvBzwEa3uMjjDV7ZZcuWyY\n" +
            "hmZ63aFxZctNr/EwR3VueYAcWJCs8aPhxFjCArX9IYjNOzAwBBuOplpHJph5VZ2bNJLs2L4zv/rL\n" +
            "x0rJg++eTh8+su8ZErtZBqA8n6fbCWCYSyAhonRb84gOvy/43Bav8snP99NP0nJepjvp9MGUIsrP\n" +
            "Kq8O6EiV81IpyrLd2eyEBI3vT+KyNG8rvJckoJXOX5dQSV7kLJK5gLoRF2/ymDlJkkQpuMK8LXGT\n" +
            "SAKLiiLSDhSpOehwdmK4Gv/FpB2wA7YIYCw81I8lkSSyXFnRBvnKpWkR8EWHfDNcu/eDD8jDb0AS\n" +
            "lqg+jVy9HBbMAiALqgV1/6KdJMCwZc//zPWr778ef0qHGJDOASaWJIJafBh62bZ70tShWet3hI/z\n" +
            "Bqw/5K/AtgABYtAfsBx6AVyUuqsrGolWFlr59So0lIlx3aDXawqrqB6qaoyqFGCQdQhh9KiBOmtb\n" +
            "+2ijsT956gozUpI1Js9PiqLQN+PDPxzt3s7KOf/uvx4qpX2GrHQn1VqRQHYnDYYxtvlZAUHZrezR\n" +
            "Z498ia4kScqyhMP+eMKLiqJkMk6efvs3jx4/oqhMFZJ4cvpj/vgXe2ZhZy8O7KKaPJhCFlxV7LQi\n" +
            "ludF8f3TePwkYWveMS1KhqmgKpBOUlsZvCeGIgUWsJE2sOV5WZhifH8Pogb+ihpf4wFFSlXGACyj\n" +
            "Oo3i8LQwXMhB0rA9nG2ulgixTBVaJxaJfmyUA9jWqfK1t8AzMwkpI8UOFgF54QWKJoU+cz/QoBHZ\n" +
            "BiXQDriwDUNYt8J/Av1/rSq1ZNXezAwhVs1dDQa4nhQJHyeDNYzRs4tcBGD4SH1+AD1y4bV9Ctui\n" +
            "bPz6PdAOBeF5lYICGa5fT8sb5qodTIbv2MP1fcXoNM2qd8Zas3cn869tL8sAzGYnKtJaqdKUWiUk\n" +
            "aPbPJ+lOAiF9qEv+w0xvx96yUTJTRNmdlB1Onh9N4mT8YG+8v0dEkqh6a9iZvZ+Nk5u6elsmkSxN\n" +
            "tXdvFwDdIFQ2vZXin4vp/rTMMggy5ybWtKdkMiIPg2CAHarK8JtcpxkIEDLdSceQpGo7iOfeeg6l\n" +
            "Uj7eHsx2wbTi/W50ZkntXt+8GylCLm0GugE+UkjLgEMVbvGSmg2HtvBudp/KGaRo/rqwr0utFCkJ\n" +
            "QVgwRF1HREjyi5yljGC9jECAsIN166VYFnGvWa3vau9e5vd/1vl2qRHplwCUwk9EK9h3qx61i7C/\n" +
            "enn5C9e/+hrpz+n2CBNlm2IPTbSWa0TxDxPFOpidIRdJH9ITyLLVEVkBLDhRShJRJOHAC64WrCNF\n" +
            "Shtjq7mJ7yZKaXtWKgVzXjGzGun561IrVZ7Nv/rmqb6hk5Eq5uX47l56OzWmikcJg/Mf8uqd1Td0\n" +
            "9a5MPkmzW1mcxMXLvHxVZLcypRioipcWjikiOZLF63wc0b/en8wqIpK8LcmpREviisHFWy7PqzTL\n" +
            "0nsTeTMlZjmKkzgpTbknUlISrrP+wtxycVYUL4v0dprGScXciOurpEiac0YEOEhq0zR1AlGs3wss\n" +
            "qKp3CWrn1YIacV3DwTLYsQFVxnzz+8OyLB9/9vlkf98sDEXKR0OF+2tNwy4we3GKLaS3Ul4YOKN8\n" +
            "wrLui+vAe/u2wGulTmrtQBca8wa51QNVaLWHfbV/A6a82JC4oX1uyRPToevN87FK7UMFSYL1YWf9\n" +
            "EHeggTsBDlawHHi1zTxf6sBH4z/tvRvHzfEow9GkYyAWJAVppYh0MlIA7IL37kMp7RuZn1eWTbqT\n" +
            "gmEXHMeSldrNUq/Aqxd5clMBwHtIIn1D5S9PizPz+NMpO8KC4TA/K46fzcbjLH9VHD47nNzfK4rS\n" +
            "n7fjLAZMsj0+/v6QdvZoFGPBOkJVzrGotNIUkXlVwmZY2Hgn8QVOjWOttExU9c7UZwYFoQbg8+rp\n" +
            "bw6KN8Wv/tXjdDsl0Qfq9qfLsHerUnGWS4rjkQYgmxkUgC8JF+RQasRaWZdj8hGKnVfpC7fh8Pjo\n" +
            "9EWRxHp+Xh1+f2gWBszWIYlVlqUemEwCIJqflUf/7VjHigRmL3ISmH72aEDGdv8CADxLFc3ryWyy\n" +
            "y3RFgItTtq5w3sqpdTH5KPFQHfUC9MufgeoRdPpTMZvzyjIkab9WLdsQuEIEkuTlghWR6BLqZrBe\n" +
            "f6N10ESWrGWrI21RSUi6QZWFdez1fMscjxK5TalLi9eFOa90nCgidloTWeb0ViqJ0p3kcfzEnNt0\n" +
            "J5UARRoCWikfEZh+ku7v7+YvTpObGkIZwySSWMXlmypJUi2016yJVPG6ILKTB3u8MMWLWWqh06w0\n" +
            "c8RkTMkC7Mos3ivyfP/WZF7kxRs0lfOSmwm/sqR0tpO2NlEiNqZ4NZ/l+f79cbKThhyEwKApCYBl\n" +
            "BulqwYffn0iVTB9OY0V1EKQ/8MHO+pzrFTOz5QUTcTJKVIRO1g0C89zY8k1ZzMt8XpzMZuU5M3M5\n" +
            "r9gZiggLNguM78bJTdIqhAkxwy7YvKt0HJuKT18Ue+OMhFzBhjSQvs3ExI718bqo1cTR2v+X6dIn\n" +
            "1jvpip//UrYfbPoj/HOtvUQA/m2EzJnLovg6WWtD2cGY6uh4dvS8YGZNhC1UNpwYPrtDdjuZPtyH\n" +
            "ovro4GD/a3xCzoYIU+6Y7gi8YCKS3VsYvASuruORpY+03QoN4h0AYuajZ0eGefrwUZLo8k158uLk\n" +
            "8PvjJFFPfvEIkQKzZZYEgLznTEfKmyqU0uV5qZVSSpPA+O4eABAlo13/MkkwHHbvJZUxez9LKMrY\n" +
            "YbydAdA3oUaaVAJn0zvj2BEBSUQkqGQuqxJJYkSpIuLzMr6pi6o6/uNJdncP4Pz4SCvK7u0BIbWe\n" +
            "cSBG/mN++OxIqXhvPCEhZz/MyjdFdjtNt2P47OkizIZPqcKOKFKzH2azvMjuacsozk0+OwJzdjsF\n" +
            "aPbipDxnCDJsyrfBRUpC/upfTcc/SyVJGzZradievJh989uj8txUDjpCuq0SpZMkViOpR4qNARAr\n" +
            "SuOUIMlZ6WWWiNI0S2+l1XwOIN1JNzKt9VdgnXqofeMb0Qe46De7pXXVe1p70KO9LigMA201Vpn2\n" +
            "R+qawS5gQtHrxAWKUzcBwIbJAFYTfnSS9nQsH8zleVm8ztnB+FT/nSIzOqI48RX+YEzFvphkECyt\n" +
            "FpodS5JwmM+r09lMazX++URGhK1aqfEAIbYApNCt0NX2jRoxzMfbhjoCLnTv4LsjZkw/e3T6Kv/m\n" +
            "20Nm3h2PvZE8uakqy5ZZCpa1v4AEqgV/89unxRvz+PEjrbicl8l2GscariQBhuUFM8COT14cpWlq\n" +
            "ndm9l1bv2EvUSUwgzZISGcMhcSClmZlGGlFKZ5TsJMrR5P5eQpIo5kjtS+L3rG8odVOhYhLIf8jz\n" +
            "/LQ4M35nLF5WxdmcFD39pwN24EVFgnVEyXZMomZUh+J1kb8swMwggI5e5MWZqVxhFk+ryph5kWVp\n" +
            "lo3nb8uj57lZcMUoDafbcfpJkuclL+a+nWph603WEsnkZpJle3Z2Qo6nD8d7+3uxUlLpkMDDAY6Z\n" +
            "KxV1AkkZ6XaKB5AC5esiuUnqpm5quq1UoJcrX9pm99lgtV6RLg3aW2G6tTUaVk7ouk6vW76O69+X\n" +
            "FYxNWH2VLt0FOwqh7XzzgclAhsxvSuknnz3ay/ZAIKGYoUYJM8NVECAiTZIiBaA85yKf7Y3H6a1E\n" +
            "giyDwd4arLW2Cz5+fkpbEkKrSEkCBFk2ANJbqRRkBQCyDLg27TcvKhJSRqGitl1UACpTFa9LrZI4\n" +
            "TrPx/nhun/7+6Oj5KQN2wZ9/9mj6YK8y1dHshATSJEl3FIT2dkpJJB2MqY6ezdIsTbbVwe8Pq/P5\n" +
            "3n1Wao+ElkTH/+3o6Nlhsp1QpA9/nz/6Ikm2IYPDI+TGKBcAsyJScaLCvGl20FFCdxIAcgFEIEGa\n" +
            "KNtWqUt9jiISpBzmr4uj50cHz/LCcDyi+TnbBZIIJDA/r9RIZ+NskqXju7s+i45HBFfMs1l+8N2R\n" +
            "XTCI5gvYBYNQzYvirADw+YPx9OF09/6eflM+cpKBoz+e4mX+5IsJgDzPk5iUUARCpNpo/Ehld8dV\n" +
            "ReWb0zRNHn06SW+ldUo/8IKLeZEQxSPdrwfFMqIkTgDO7u2mt5NkpBqDYofJl4P8wkLlpp3a1S02\n" +
            "PvY3Idd7zkeF4fT5vy5EsK67rgFXNbe1pvK6jSv3oJtVvutBWfZPbtzyhgmCVSSzuxkAkAKzMQzY\n" +
            "5E7KC8CxUsoYA2ZjzDffHn7z7eH04Xj6cBorbQGQBFtYeKvh0R+Ko9mXXv73qe+SEf3rv5xO9idE\n" +
            "TcrtYJaWAvJGghqjRYJ8QrH8x/zpPx1M7k+nn6W7t3b1X6XFm39/9McCAum2OnlxfPTsoDrn+Tkr\n" +
            "wq//6lG6MwFqSErtrGaH7E5avCqOnh1PH0z3fzbx81YZ8/QfD8pz/nw7rc4rpSi9nUFQWZpyXpZv\n" +
            "y/mZKeaFFbR7f6LvZorqQhrOghFK6CysFDKJNBaAYzbGCKZIKU38zkhQdicjouRWppLEMH/59dNY\n" +
            "6M8fTNXtlASSkfI1NqTjylREkKT9YsuyjIjYEUUagg7/cGKsiZOkyE9lJMf7k4r56NlRMkomD/aZ\n" +
            "kb8q022ttD78r4fFObPBl//Pl3v3s0cPJ6Q0kS/ECGY++P1TCDz6bJrESf2CCEA+z//uP3/56OHk\n" +
            "8RePrGPmKpQSF7I6N8xWKZ3dzaRAdV4xLPVAPnb1bJd1/HJt7WrQeJuu28vpg3C7PVpvehjw83ce\n" +
            "3P7L7a+bhEldRv2urMUPeXF6sy2gm/B3YCMIspAlUmZhsGAwf/UPTyH48S+fHD8/KV4Xv/7LX8dx\n" +
            "CsdjpX79P/76d79/Whm2jsNwBYi0ZcARhIaYJyOlIi0FSBMJpLeyvXsZRXT8x6PD72b2vQ2oFUFa\n" +
            "60SpLNvN7qQAQsZoglJagkpTHT07yl/lRy+K+ZnRsSrnJn9tijOTxCrbST7/Ih1nWRIn3iUuAV8b\n" +
            "A4BS+vEvp+a8Onx2lHwSTx6MpSZr2Rh18O1BecZPfvl48mDyt//5y/xH/uv/+BWYATKLuS9t4PPY\n" +
            "pnFCdzM4DpnrSFsiAGYBhiJB1knmigTpiPQNFNUcPt/+eUVKp6NExamJaPbtoXWYfvZo/8HUgL13\n" +
            "gzl8ICUlh0x+JCi7M87ujqWQxVllBWavi+I4r87L4szoiL75+svKcBJh+umjyf7U+hyqzkqye/fS\n" +
            "RFNZVrMf5uXrGZx89OkUIy/N8eH3B8V8/uu/epxuJ+HpRMX5fH5enTzPqwWX8yr/sYBAEiutavS3\n" +
            "AykJwcwWQjZvHC2AVTaraEVp3SyWcSn35hWpseGvRpRfTBeENK/o/JdChTYR+5sp2xwm3aCsNtBV\n" +
            "Lm6tU6/K31gbaVGDSQRIkGEuy3mSKKmUZT55kavoGz1SYFaRAjB9+Gj3dhpva3+jT40NH7ztePKz\n" +
            "7Nf/w69UHMMxMydxQjdQVdX8dVG8LE5fVuw4iTwGjeevi4Nznu7P1ehxEickyb4L0Fe6Qbww+Vlx\n" +
            "dFwYBkDl3EAg3U4ffbo/Ge+lt5S+oe27SgqA5/1Jhozo0aePjp8fVWwm9/fVSFeVgUA5Lw6fHe7d\n" +
            "H08eTMpzMzclRZBgUqRUUswBgemDqRT43feHOiLdGtgBR/Oz8iQvzMIAgC9PwtCK4Fhtq937e3BA\n" +
            "BLWTmteFGiUA8h/z4qzYu7+3ezsrfiyM4Grhq4ZzrCgZBa2bHcMROy5+nB09P5mfm/JtxYzifF4a\n" +
            "qAjZjtJKJ5qmsUrTNN1JvTYhQQwoJce/eOzT7J/Mjg6+OyjPy7BsBOev84Nnh5MH473xHkDmvDrJ\n" +
            "c1/ggID8rCznOHg2m72YxXE8fTiZZCkArbT1BcKdV3g7iY+7p329UIMqOni8X2jP+mDqBYNesdlG\n" +
            "f1+lle/cmt9X3XgfLPYPUuvIreGcg79uQNZ1hLP+jUQkBTMA5iLP2VSFs1/+l69O86J4zV+dHQKQ\n" +
            "RCSgFE0f7u2Od0PUhA9eC70KRn4QwSF/VZRFOXm4R4aKs3w2O1Gj5H//XydKJTIcIFS+Kr765qm1\n" +
            "FgAJ8PvaRRqpJE4oSrJ748n9KUg//e3Tw2fHu3fHjx8/yXZSY8r8x3n15nh+Xo7vZek2dOSRvzUS\n" +
            "2bEUGN/bS2+nsdIey1At+PD7byjiR1/sqRGfvjxJUzl9+Hkcxz5L59d//01VVbuZLt4UCZn0JsEZ\n" +
            "X/HSo2hOi/lXvz2cz03rXQeUQMKMGMVZ+flnUxURnxVqlMCB2ZzMZid5kXzCf/tfvpy/mZeLkoh4\n" +
            "wYo4UerJL6b79yddmzEzz89N+aYiJct5ZRcY31LTT6d79zNFUhOUIF5UQXBw1m+yvKjABo7ikUpU\n" +
            "QqB0lJJQBBhjjo598MLe0eyIHIqzYvaisI5AJBWKcyTbNN2fqIiSkUqShEapjztiBFAdRR5jsz7C\n" +
            "p2GENQfVsgy7fIL+6YACF5z5nvrFB1d5rDe8IO1fQe5fGuomMs86HEUz6Ut6ymXggnYPdjDM+XlR\n" +
            "lmVxxrPZyclrAwH5eu6ZWxMm97N0JyFQEifpJ7GiNsks1+K/9KhYEEClMV9+/RWRnDzco4iYcXRc\n" +
            "pLd5cn+sRnW2WQE7IhJSStKRZvf/tfctL3Yk6X6/a2LgC0hBJJQgE0qgBAmUohumiitwFb6LLuhF\n" +
            "V9OG280YPPoPbHM31zv/C7Y39nhhGC8Gxl71zGJo9aKhejHmlEGmakCDTkMLUtCCPKCCDNCB+GBi\n" +
            "4UXk+30epe7re79F1Tl5IiMiIyO+9wO8ZgIB7Cv/7ONzCBkEkV6bL//w7Pr62r2O6+eLxTdfaaOx\n" +
            "zthydqOvrhf//u+e+ooAV7OsCmUljwIVusfMGNd/ulo8X56e5NXKeK2VhyhQwUEIgZUGmEnIIAiT\n" +
            "JAG49AY1ha/e44fB07891TeO24FSPixgTQidwen/AIBJAmBAs0lfpfr1itcckn8URyfBUah85ix9\n" +
            "nVxfL9M3KR+xU5c4J+v4UaQCBQvNePb1xdWL67OPzs4+OgOgdbpcJunrJPk+CX3/i188BfnqwDd/\n" +
            "SYn8PPUo68X/XQCI48iJEpnO9I0++vDIJzy7XBzHUXQY+oqC+3EURbzmX/3mtyTw+Sdn4YGCZbYZ\n" +
            "W00wgPRJ5kk4bPsUNGXS/OO89DPDEaVNYNvU29seO/EWMOmqW8Spjfc+xA7MuXc+zOhnJEdShVda\n" +
            "1VQLtc3yTfLsm6vl90nGrG8YAAtE99XpSRwe+FfPl3yTnn10dPTose+5c8t5NT5QVjjnSUGA5L/A\n" +
            "/AzJml++uFqu9NFRePUm0S84efUy0VplPtYr6TlTojQWvNZgQ3cIhUOUFHA+6oHyWRBbk75JLr59\n" +
            "xppDRZlOnc9ReC9SXkweXXxzka61cUW8LEMUboh5LGrhqCrAa15cL1c3AHx2FniBKAjIg1sWKk64\n" +
            "tCbwiDylLRuRe9RIAQiODmT00XGTuesQEmYoyiyzOwzv+OhBcP7Jp9G9WHqENadpkjFB+UuXMdzm\n" +
            "NZ3c5EnhsQqzNV//8erlcumGvvjmYrVKs0xrnQFI3+hE6dO1CYkgimpaFgCSV1fL76+PPozD+wqC\n" +
            "yUIC8YMoDAIAWOvgkM5OjtiCQJL8dJVGPmDhIyNX0EVwkXOlFswDU37No/S7m3+vfjubetN3Q/d6\n" +
            "vcjnMOZissX0r7cEomHwz0nTnBvLyo0NcAcPwUF4FIVhEDGbr765iB6Gn3/2qe8pMF98k6apju8j\n" +
            "AyevEyKKDsNc+cPG92S2ZqkoSZKXrxK20O9+nb5JU830IkmTRK+ZBPMa2mW2rw1NIAhJpPLSGsUP\n" +
            "2U16vUzobhg/igFmZhXQ+Wfn4UHIQHaT6bVObzRuDLuivTbXTlXAwB0gM+Q8C5iX371cvkgU0eL5\n" +
            "1dlHR9FhePzhEZiVS+nvqgAAxsAwRw+jT+ksCkLXrfTIrJlhfJIdNnBwl5KAFJA+hRT4d9Ty+2W6\n" +
            "SpPvE+aMOa/w6YqaFNp1gCGJjOXk9cur5wu2nGp89fUzBsIgjO5Fp09O1V11eblIlksuPDKoYDQI\n" +
            "uPjmAqDHH8TK82EZRMFheCpAnp+uUgiSwpfkGBYYEHl+FETJasUWjDy3gHOJlS3FUwH95H2vJx8N\n" +
            "O1qpaDBzOetJGD25Rfbelmw/7+bbhV7+v8nzDzECveqW6EH8NIhYEAliQdmN/urbCxRLHsfxxTdX\n" +
            "y++W5x+fXT+/+vL3z06fxL46g4eqYKM1mUb6JmFmvYb+8xICihAekArDY+Urj66fX7lSM0BO9gHk\n" +
            "HukCbusaC7CGNelNurhcqDAKD0MI8pUylpn54tuL9CZb3TAJDg9UFEW+p9IbXSky6w9rDEjCMgQl\n" +
            "q/Srby6DAxXH0eLyUmuNwygIQoAl4N8N0rcrIhWqgM0KAmEQRoEPAUlk1s6JyFHp+e/JlTkEAenb\n" +
            "1cW3F1cvEkkIgvD0yWl4GAJ88e2FTtNMs/Kkc8hn63CdSdNVeDeElfomiaLo9J+fRg9j3yNjsbpJ\n" +
            "zVqjiOcrQ0tJIF2li+er4ydBFEXuwd2EgyAEKOGEiIzlKgrIgoRSKsRqZdaslEvNIBnIk69aA+Te\n" +
            "h8bu/4SPQUWiB7LgzJDee2DGyZ11uLucxv5hRtWxBsz2NSqdsQiARySUXjNy5Z/UqV58e3F0dBQf\n" +
            "RvHDaLm8XjxfXvzv6+UbPj3xiXxYSMESwNr4BzLT2dGTePF9om/M2UenDn38/d/9u+hBxGyyldY3\n" +
            "Gb9lk+dyLbAGORsEV+dKACSlcr7lLj4CygOtiQB9k4Zh9MVnx3Ec+54yrC+vry6+/h2vMyDKBZDy\n" +
            "CdeQHjIXBLpmCXN2fpYZf/HHy+ydyYNeiYxFsly+XC6Pj07VQZh9n/A6l0GS14mvlDrwsWZ45HtU\n" +
            "454qJ8gBIPIoLzdmcfo3p/HPj2FNeO/x8vuXiz9dkUCqMxKQefCCs7ZACgIhiqLHH55dvXi50tmn\n" +
            "H53Fj+Kr75LFD8vlcpm+SVdvXDhgFTRJRMy8epP6CvGjSHl+Pk9njhEEwbBGEgGq0LkoACAZhuHy\n" +
            "VcpMyKsJMTOzhSzyAhg7rMa/PSjliB2U3HOhOYSoro4LM/sWdTr9d858H9vvoFHeo6+zeuR/nXnj\n" +
            "NYM4OPABuvzTMnmdZGsoa47imMFHfx0vni//03/5NSziR8HR0RF5hLU2nJsPjTWwiO7HZyep1hzH\n" +
            "xxd/XADMbNLXiX8QAOCMU50y59ZsgEFKEpMktgxhIJQEXC46RUrd8Vdvnasv2IIUnX18dvrkNNWZ\n" +
            "fqufff2MBMLDiCzSFWeZBkN6NUogIAlgQ0KyhX+gfvmvn4ZBuPxz5uJYAFfMj1Zv0v/4n38Nq6N7\n" +
            "se+qbrGWRMnr5NnXz4jo6S+eElHhLpWjrVl4n1mvM7bIsix9myavkiRJk9X/hEV0GBydHIdhmL5N\n" +
            "0zdJdHCUsaY8tQGkoMf3o3RNBBjm5Id08fzq4ttLAOGDIIqi6BDLV8skSZTy8wBbywCi+9G//Te/\n" +
            "DA/CnMmyKPkDKcjtJSkcflfZGnqtyQOp8PiDYwjom5VZG0kgknSgzDqTJOtJPvp99fcCPUWragbp\n" +
            "CmSRU8zd5WqW7xnKij01O1btRwcNM2MThmIGBwoGDMvsrcKjRThX+/bNfSRkiTsISimt+fr/XD37\n" +
            "dvHy+6VZ4/hQPf389PhRzMDpk9Pktf7V/3pGAlF87AeRXrMSBM7KeEmpyFg+OzlmS8tXCQlWYbj4\n" +
            "40X6Q/LFv3oa3Y/pDtHahyAIBSJnIzAwDJBlx2Qa5/bLGQRLRdkPSbpKwwMV+n76Q/rs919ev1gm\n" +
            "r7WrsaUERY+S4w/jKKDVTcaWJchYcpx8XgAPUlpIQXQQ+neI30EpP1RKr7VzagBzkiTMfP7xeXQ/\n" +
            "YmZfgd9lhrXTAlxeLsODi7OPz8hJJcjxHdf9u/vW3zlokpBR4PM6r7qX3qzCIFIHKo4j36PkNWCR\n" +
            "vXP+cNQiJMpTvu/7RMpD8PPH8YMovBeGQUSE7Eb/6r/912yVgo2L+GLLRBQqFUWhyXQZ1etinEoH\n" +
            "2PTN6upPyyg2oq7fAAAZgklEQVSKyePku2RxeeV0n+kPmauznmmWRMoj/8AP7tLpkzh+dAwBsEGB\n" +
            "CNp7z0FrY29KFAeTiDYjxkVRZbAG2zD/zSFaIMamPsR42I3djHaCzno1NDE9JbTqLY1Lt8qW9evs\n" +
            "apksni+X3y2ZVHzog5kIYeCnOmULUPjpR6fXL5bLV6nvKQCwLAkQkm2W68ksIBhEihS9RnaDoyeh\n" +
            "79Hi+fXVi+tQhUr56Q8rvWa91mRVtk7BWL1ZZW+z8K7P1kjkznmSJAClCM4ZjiR5ipH6Kjw58Y+e\n" +
            "sK9UEIahp9gyMy8EmbVmNrAqR9Zu5zHIQ8bGv6NgOMs0QNIDA5nOeK3JI4AyrZWHOI4gwCYDKFuz\n" +
            "Cwo8fnK6fJEunl8dPzmKDiNYrla4qjXau2tLh+U8Pc7p0XH8MCaBq+/T7HWWrlIpkK0Z0FqnzEf5\n" +
            "e8m5s6K2r8kctYjuBZL8jDm7SfVaZ2+ZnVyek30wGwCZ1s6RXuYmmAK/W0AwG8OW0jQza608mb3L\n" +
            "kiTVa869MwQUqfAAOTp4k6WvEQYqup8VKT6GaUx32w8dn9xwsBP0Et0uUpj2+R+YZPk0E/rFIazT\n" +
            "TiHUCjluzKCzFr1LPELbd8A4vM4Wl4tn31yCguMP4rPz8+DA//X/+C04o7wOtAScUoAy5sXz6yD0\n" +
            "T38eOyrtXMSLPI8SYF7rLEul4MdRoA788JKSZZJ9mEmP0lX65e+e4Q6Fnsq05ncZM/OaT0+OnY8g\n" +
            "LAxnTr3kPNuzdyYSFN2Prp5fA4iiKPkhTV6nL1+lnGmts0DJTGte+2xhckVGvkeJJFtIIfmdBqiy\n" +
            "/7v8OQDAEPAF+Uq5PCL4mXRU1LU8ehQvn0RffX29uFyoT0KiXv/o/vdSajSV8oFVts58FZ6eHKvD\n" +
            "LAzCMAwBJMuXX/7hWeYcGAVVhf1y/MXRvfD8kzMXC5yvsWDnAqA8enwYKY+IoNf88sUyfhCRJ8Hw\n" +
            "BVxp8HxBClWCuuuff3ZEAtIDeTg+isN7PpwglpfoJqcJYma2rAR8ReTUBzD9NK+WetRgB1+dbqKe\n" +
            "/PpmPvP1WuYOGt46RZ+9A5UNJkwB7a6LKxtFO+8Km7P6VcYlm7MGTBQ9jD4XCKMoPIzUYaTfrBxN\n" +
            "Y2bfuWcTLf+8XH6XRIHim/Ty24vQQ3QYwoWjwJTJ4dzy5UVySIYHYfwwurxM9Vo7Kf/6z4lSpD1S\n" +
            "5Pu+H0QU3Q8fx3EZSWpKl/FSqCNSnq8tfvv7Z07KIFK+50MgOAgfR76UBI8oJ3cEGDMiB1koRfrG\n" +
            "UUifdUa+IpdHzxXqZc5uMraGBElF8YN4cbDMtIZlKais5zsL8vQeyLLs+sV1cE/DmPBeCLhYRkCw\n" +
            "FFguXyavjgAG2FcqOAhLR+wgCMkjAMzL7B37d5XyEAZRGPokyMn2YaDIo9WNzrSOlA9q2uecFyAk\n" +
            "BKL7oX9wClew3IJIxg8iCOdsQLVbAIABvywNYpE76g+zw6ZHPt8E9uHhx2iqABrr0HdDZctr5Jub\n" +
            "9gCoBqhVAmy4BvUq3t+jG2M/5Pg7t1cTyaMPHh89eszu5Fg264zZsGZm49Jt8nqVLJfKwxd/ewbg\n" +
            "4tvFb3/z5aefnJ6cHBs28KSxmaTcXyCPVAcrRcqj6H50/XyZvk3UQRgGFJI6/+Qs8kPyyHfUpkwT\n" +
            "ZBkgKaQLCKM7ChZaMwNRHJ9/cp6uMl/J6H4UhkFw4DtTVujR8nWiCOpAZWsmwaCxgpDkkR/6y+tk\n" +
            "+d3y5K+PiWR0P+R3R2EQOL9kX5EUzJaJ2QDRh9FTnKs7lCfbs6VhchLMaqUXz6+urpPktU5XC8bC\n" +
            "TaBIjEuwvLrR6ga/+u+/hUV4gJOTY3XiSw8ZG4fOpPJPn8SPP4wBKE8yoEhJIrPWbBkC8cPoi8/O\n" +
            "SJLvETPnfkpoSd15aL3yfLbGRVeya2BNHp0BgMF5tn5JoijmYfsUT52HnbEg7wO2Ef5baoVpSm0b\n" +
            "n/uHrKGAvTkn1GEfwRJF4LAhIZ3JXR7Q2cljsKGDMM/taU0UBU8/Oz09iSEkWf7dHy6+/PoiuOuH\n" +
            "D6Ji6HJ/MADKrWIc3vXDe7T88/L8s+j84zMIff7RGRjM2i92nLGAS1+fu+UBQpLySYXJmzS90fHD\n" +
            "+OkvPk+1DpVyeewJzr7N/l0/zm0HxKxzBQQYA/YOIsQPouX1Mk0T5phIKuWf/E0IINOr8EB9/i/P\n" +
            "fSWVl1vylCdPTo6ZizS4hPkbLFvz8tVSr3X0KPB9nwQpz9frDAAJcqz1YxPSz8B/4eCuCg/DKIoc\n" +
            "TvQ9MsxOtiKSQRFGrwBmnSdEgnHYQT3xIVxoEKQLslqb0urp9AKmwFwkpHQrxnABuVXe5pLHrjtN\n" +
            "VeTRcI3UtWHv7n32dk6Ng2q2PW9z+PBv/pC3dPKxdUoPFFVictnShWSDOYOVypNnH58Q+eR8TmAc\n" +
            "dxA9iBRJCDo9OWLLV9eL1ds0+iDmNftEOW8J+ETxw0h50hWl8AOK7seLy2swnz45ZpvCMusMQppi\n" +
            "s8ouP2lBRPIOLZ5fZ1of/4vT0A+MhdaZ0azfaWYD5vQmdYSOBJyBKv4wjg7DZsL5JjDHcXz+WRZF\n" +
            "ERXeUE55YQTCIAwDHyXRE5W7MWppjueBDO8Hv/zFFxBKej6KA59zEAAEkXMcYmYYpVwbGDBrU9j8\n" +
            "ZT4HADaPpyALKeALytbGGVkZuY+Ae7lOd5An9rUG1kghpaBCNQPDWZ52TUjpkqZVD5U77wIdgg+A\n" +
            "Cuef24TtVfctqb7+pup7rHbyWzS/4Vg4DaImMMyXBneH4gE24rekgLFUeObDaXEK9sERAcOOAHOW\n" +
            "15a1EsI4pCABIAPh/KOT+GFAymcLCDLWgAt+myj+4Dh+xAwDcKDU0YcRwOQReVAiBLN/4JMkAuuM\n" +
            "cx2yaFSVARAdRuefnBuL5YvlxeUSTeVtmYbNh9M4AkAU0FOiKAilTwDQyOPIZR6R8H4YHp7lr0oA\n" +
            "1hhtIKRUCpYzNkChESz0neTVF3uut5XyfPXISfgMC3hE7MQH5jW7/IggGGekcB6BAr6vAMV/YYDB\n" +
            "xqwdDpIGzGuTb2LnuWjZRStW28+yYQZM6dDhXHSLanyOjZcMSe5GFLt3aL83pdT3fPJ3wgKiyblM\n" +
            "0+wqFOCvXv7mPwzPrWhX6lTyr7XrtcHqtv1p8wMKjGv7sG+pfp8P3YevFsXtBpZEENJtEacKyvku\n" +
            "AV4bn6SjMNKTho3L2GcEpKDkJlPKJ5vxmn3PNxZVxl6RHzdYKYmyNQAQgdfse5TdpAB8FxXnJuO0\n" +
            "/ciZ/8wqZkpWq6vrq+vnC7YgzycgvBcqpVCE7pIAr3P2ODhQx0+OlXIarL40pwAE6TWHXl4x2pFH\n" +
            "EOVmPMc2E/AzwLj5mFwNUahI57J+AhCUrQtNPuCyJDFrX5FhR2EJzNJThuuWeWZrIOAXxaAMABSK\n" +
            "DIJZA6jVULKuII90TEqRKNXkKF6QqaRDF6HkJJqiNIOApDzDYodyNkt0DFXFc52PaNQ2YZbHk2rn\n" +
            "vXX1buh4FvSoJ0s013v+GIW48Vcvf/P3xdUpej7FJZScRIfH6O2tk5mwZQXZVMk/00OxVBRtAZVY\n" +
            "OKNNM7lYaTkfSjdW1XV3zejWiU8OTazUuPIjQut8zjhXpvzvbD3lApbMY8NRR9ZO8uylHpqGGDHs\n" +
            "1wJQbHsL1bBMIdfUx8oL5MocHw0d/snpVdAYoebkIxr8ZtVXiUKm5MCGT8Lk1umaA4YQwbaZj1rQ\n" +
            "k+HX9lwfg77FLSctW236uh0ZSwoUVd/f17FvwV5VWe8fikMmqwgZ/BQeKp9MnhWmt0krA38b+Xac\n" +
            "C4d20Tj962t+i9Ajvk56CnRht+RnbtCem3fcFnO4gA6YUXQjtxB2/glQbfpi7do0Hz8dG10f9Mfh\n" +
            "Fz/N9eqftQ8bx040/+9Vk1fMpukfXokGow6JBjMVB7Oh/vrrkT+7Q/u8DvY8rUgbxw7bQd0beii1\n" +
            "6cSVHxHm7Ok6t9Wj6779kz/I81cwaxv3PWz75I8tCDcaTCnmxUCLmiNUrW0v1O/fRm/ZEv5HqxRs\n" +
            "0m3FPvUy5PuisLLW58bQ5NPKUzozE3nVSRd+fHZ3N+iK+nN42j6peMud1PWetJsYQbqwF5TavwLz\n" +
            "j11L5u+nwb0yf/8YXEMflRmhpXXvPHljyPpRry36rjR/QxFoM+hY7Ld5u3uZ3r6e8aeg5xuFgWPc\n" +
            "0Lq07BTd9HsbQP38T1Cj9q/l/u8Pbx2CbuP5SrSeTtoHtmVicAq/MnMDSt1e3XOYwGNj10yAY4z6\n" +
            "kLRf4/BR+rRVd016XI5Cbdp7UPj1yfkVORjSwdam0FX+1+cmxa2o+2Y9YJ1b/gmf/x5oGHTlwAL+\n" +
            "aCJ/lZxzC9w6RUELGDTpjUOtP9u4h3rshLUTOqn2bw4yJOG3wgG5y4f01iofyalcNrNVs543L3Kx\n" +
            "38DkKuKGTcbIXPoYHqKA/rPqrOWdo96bFm4yN8kWsLHuoI7X5mzTaUw3G2zHYtJl2UaGEAXBzx0o\n" +
            "TOUGLspepQRM3QoAUzXoN93Lnq+26RQ4LOo3DjyKo2vbY1UuWGhS3Pr1nrnV+ykJbv1uGvcjIABi\n" +
            "M6akBi081B1JVH9HuHcq4y5qm483DG8cnuRY0Nt0IaAd2Gm3/8qzt18FXnusPgXB9iPOvPFWuYPu\n" +
            "yjeU+Q0wNd5rWC6Qtbi9mXPo8PxCNqZQhgNhwBuneZhpeOhph5/23Hr6aKLjQbJfc+vdJSlHPx8y\n" +
            "y8GnDS12+v1wnuPSVEmrAWxLkG9Ddb/dQJMuRg24VV1JH5Q8l0FTgWrzi/WWbXBtBOTYnJuOfbvD\n" +
            "SFc17506DEnEg8r8udvGJYOZi2jqILY6ZlyqBlwfg9ultRBDY9kGpzAGc9N71pptu5V/ypbh24WZ\n" +
            "yzW6TScFnOnOG41N41/fgA3MsJnf2/hMmsJCa2Idmj8uak8I4rePc0tB26WV2U5bUJPNG5zP5gx7\n" +
            "jdrPigWY1ecEtZgE0/i/kyS+GdX9cWG+JL/HbdqxzNVNs43TPk5vb9vk6UJ9UVQfqAWa73+snXvr\n" +
            "FejLauUOxA4xRdxQM9hpp4K94uAazKoCtqmxYC7hn88gbGa93ytMj/tj+wXUtK155oXiMmofxhY7\n" +
            "v2sLmXG+U0lDP9VSRQO7xOfV+x+DAdW5aLL687hdsVn63c5UKnumm9kW0b61Bc15kikWoLXE0+FD\n" +
            "u8JeDHAGgLE9/ewLI3Q57Tk9l+EucqNnHNGTbwHdzZp/NZUyHwM62lt05WgrjNtB8HZQkt8e5kup\n" +
            "VUvnktfU9g8homL+nH/s01i6yu3UuacXuovSO97QvS21Y89SzlPOjcFGWKAz3BaqO7kvrcGQJaX4\n" +
            "ach3YLzDztw6WpJ+A1hzt4zVtzEVuc5vkQMz7K5TMZliONPCNaLWpoqZG+XAeyWayRX7UbLRTSPW\n" +
            "0gevflaq5OUjLEjlhtc6TLWAvsl7i+8jyo/OlSFuguofets00eFeIxCqWG5Tv1j7NP7+R5CKHPk2\n" +
            "H8Qw7mt+nYubBFAv+DOVsrIEA1MhBefIMPVQ22NAl3sH1TNKuCJIzRmhtQj1Afui8d11W1/VTSfY\n" +
            "wJLFVsw76QbhlAb/XSWCWn9Derr20MUz9g5NrZWbCLaZhLryv3d2fTus4eeAfjak/0oFfe9vR4a/\n" +
            "oH6mdmWjbbJPR729awpabFo/y92BTjMjTJV+owl7oZgSbdG/0X2TmBeYSJppV1y57ckfvmXEwnfb\n" +
            "+pSu330x4pDF0YEo/wy13nwqVcdzoItxdsVBe4FevvGW1HVDDPacZvuDScOG83rc1Y6yCzT8vtH6\n" +
            "3MjVMbFWZkumZLTbPdH2fUCpQZtotdmenu3DM99oVAQt75U7Kqex34wg75H2dxawdGVrCrtmvqJu\n" +
            "5HjXNe1T3VVUt9DA3QoSqPvk9av66pL/xsbdPQvzY/t2/1i7eQyLxakr/Oeco01oWb/kOQMdzHRa\n" +
            "ss3Pu5PZVrAg9oUCZsH8zTUkm1a/vic73ATtr2xpJYwwzzu+vmqUOpc+b+gdY2+HYSPKRBXPv5Xb\n" +
            "68hottZmpnWg7wxWrjlz3HpQTX+cN9/uaZuwj/O/5Tx6MUUj8fMtQ5XF1OX2chu5Mno5kMWvM6HR\n" +
            "tKZkaR2g24gs3BZmoNCumLmh4PkTheEj3fLk3ZpZzk19XPvWP1L/VHZm0osSqyMTqDXuaZM7XfVI\n" +
            "yO/LSDPB10xTocmJys4HNBn1obtGFN+1M9+dYR7pmFvqaqKThDRi/tqaZrsCq/RUbWxtMNN3pLu6\n" +
            "/VGwDQQ3ssyN7HrjVsCZPKzrtvdqD6Hup6Pl1ToBG1fgVVBfvWFtrmjMpkNsewejnlm1oF2GeUs0\n" +
            "vDPxz5OfbgqDFURnbvqaVJ6/hg2pqWgT+THYyvGxc4+ReejbhPgvi57HMEuloXAzqVqaDeQhd28f\n" +
            "z99o0HdlmhPewQ+jx+kghxKDcOPX2gFpaOaL4zzivdL3LL3mwznMewtGS3SXI6H5PHvUx/d5BDYe\n" +
            "41aLGW0KG8jeTVuURZFSdlzAr0MjL0WXM69gAD8241H6uYY6ja0ulo4PDmflLphyoIcB6FurHr3d\n" +
            "EHLvabn5ca3s+d0bqysbbDCRt58Vn9OzAt37qB2NW/5QYA2qXPeIat70rb7aCbpmQKdV3f+hMBh0\n" +
            "cu8gr6xeH7eN4Tq3zJhTN88n0EZvrZ6n39wkj3obWsAhBdUGjIwBYFD4uuRfHbSUEQMdCNS3uCnZ\n" +
            "+Fr/jU5Ex3jujk3huGEGiP0GJ3LIVjd2y/zeW+AeZ0OUMWwhL3/lwrtx9K5x/FDT0tuBLVwcgTrR\n" +
            "dUk4Gv48Oyg4Zt3E3QjhmkvDHinzdId9TkuFwF/3VPsxvDK3g5HNnR+5TstBJqIFNc18q6uuk8zk\n" +
            "ZBrntj6BzZe6K1dPeHDtgp07Pr+tsdqqgSnYqHETavqpZprdWbrz/Au1nOV2kIv7bu17Km5+bnA+\n" +
            "otkqX+LZOKHzSnhgXr2TgUuT5qjcdlSilzXY3UEQ+9B69Lqj2JKqbzTJZvuN1qqL+reZAIByHeQM\n" +
            "reE8or0p6Zs67WMkOy8TKpnNrrSvDAraeGMM+PNtvsEG7hjXQLShUeSnYxHE2ALlVlCafICJOL+u\n" +
            "aqDPbfE9qg8kAIi61nrzczKyTTdVhTbab8UWtV7QdrpYAVSCzA79jMO4on434K4GoctKDMBm20+U\n" +
            "bvzVae8Ve7dW+w0s/NRRbAxQKDCHR62hgLbPw8BNHXfl4vv7Or/97EB/204lpppRag9HbuTG0T57\n" +
            "duEOx35oAuOse+tXi479b2CIUt3gRpmYRkPHVlHU5i1tObkAd6I2UJU39rBTfo3sZADNzSDIac2K\n" +
            "pCDEtd2eFzu1DJGbzKima5809c15Chft2w7sqeZXLqXo/FTOEjNtKjWYZ7PZ1GgxiQ82xLjbUont\n" +
            "iWFfV/MbD014fDJDXQ1NoKvlasF2Ms6kZ0ePsXrIBrI93I5bft0iXuzB6mWVKbBHPPkYdQl/Jns/\n" +
            "e/cKh3WKWFoqlnvamFeGKzUCckfau4HEZEMn8/94Br7ND1L/XPfEZ073OTS39zyBtpmg9tN7g8IU\n" +
            "txFsnDx3I+gXCnjiJIvi5I/31r04+1kY+Gc9l22LsalgKN0f96MvLrraZnL/YMChy/IA/P/3gFMg\n" +
            "e33vHYja4mwB7xl3FHDLlKc4KzVWN/+Q090BXmDuajAsD9XXqp9KQUTNN8dtFF4z+09zRxXfWJki\n" +
            "nWxDosgxXEyLBA27Bgw9f78jZBfmUuONrM0/0l78icOY056dsXTdBt0rPcx/BQ1173bvqMmq7EkK\n" +
            "cOwz9V+p28jKpCA1ffmGOoi54mpd8SGGTz6VjaqfRKG0G1viejqhVm+th9q7o8AoDCmBdzzS9X35\n" +
            "jxM79KrrWntx/jrXRdwZDO1+mPZytrf4Hjn/20A0DaG5TLw3ePJHHnbzdRDD7EG938KHb94AA95R\n" +
            "eSeluaITk7M5wr2NV7WFBGX36PD8DwwqS4do7BC2TM0lYcs0osrp4gvMWv9daX4DSufZFnHeHPKd\n" +
            "yXUi2t7fZRXMXSlQi4gShMMjEwdqRNs/up/HTl2PlY5aKKYQAW4PRghCK36rKYbMO/B1RmuONDSn\n" +
            "qx17aMK448PEBLbeizu+0Lmo1rQWvBY5n391r2bsvUxYKLlBlqcNGSN2vuHQnfq059Dg+vSqycwg\n" +
            "OgN7rL9iT+7PW+Ktebr3+gvo/7XM2FNYGrn6pRfK6MAdVSD7u7GGVko5rTIp7y5EvAfo3fcto9rM\n" +
            "BylL0LZAFKGftnNlDojmJKeQQo+tu4P7GoGo9R3Vda23tb065E43fqUzsZn1s8ZhWBHQGx1XhAA0\n" +
            "LYUkiIvAnMG8/R2oaSJyREJzUXWLNjYZvIpG9ey2DnfQiyA3tAuOIKlx1rTwI26rKhprOB6G2DH8\n" +
            "vFe1x2RFt16ue6Zhuc+m1bv7ufPR4YWKBRuNEJtJImvnHMDmVSlz4teZxqjqcWQyY694F2l6/Jdu\n" +
            "U1udqU2XhLeRb7ejh7XX3E9eNu+wgn3R5z7GhAscTWWSkr0c7/dpRBSdD5tDS7X7vnUirfdie4lE\n" +
            "J/FE+cN74+B+PNtw/yP2ueI0HZLG/dL6+MCiR2q0aVPy2ijFW5k6+X2bqtu+FwHVHNpI0GzW1EVZ\n" +
            "A0JWub2qZ6eCPSPUWbVeiiEKOWgOPdlkO/Y/RlnUacS3ZKMRR5Z0eD7U/VhwpFPjtfuvbqjzUM4f\n" +
            "tj2TWuei+7VoYwHBPfMQZSdjMnxrRGrr9vtgXK7pNU7XJ9zbpviJAAhumw+L2/8ftVeWLT66T6wA\n" +
            "AAAASUVORK5CYII=\n" +
            "\n";*/
    
    private ProgressDialog progressDialog = null;
    private ShakeListener shakeListener = null;
    private PopupWindow popupWindow = null;
    private ShakeBroadcastReceiver receiver = null;

    private LinearLayout friendButton = null;
    private ImageButton portrait = null;
    private TextView userName = null;
    private ImageView sex = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        intiView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.shakeListener.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.shakeListener.stop();
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        super.unregisterReceiver(ShakeActivity.this.receiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {// 防止连续两次返回键
            //这你写你的返回处理
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                return true;
            }else if (popupWindow.isShowing()) {
                popupWindow.dismiss();
                return true;
            } else {
                if (getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.ECLAIR) {
                    event.startTracking();
                } else {
                    onBackPressed();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void intiView() {
        this.registerBroadcas();
        this.progressDialog = new ProgressDialog(ShakeActivity.this);
        
        LayoutInflater inflater = LayoutInflater.from(ShakeActivity.this);
        View view = inflater.inflate(R.layout.shake_friend_popup, null);
        this.popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.friendButton = (LinearLayout) view.findViewById(R.id.shake_friend_info);
        this.portrait = (ImageButton) view.findViewById(R.id.userPhoto);
        this.userName = (TextView) view.findViewById(R.id.userName);
        this.sex = (ImageView) view.findViewById(R.id.sexImage);
                // 回调接口
        this.shakeListener = new ShakeListener(this);  // 创建一个对象
        this.shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {// 调用setOnShakeListener方法进行监听
            public void onShake() {
                // 对手机摇晃后的处理（如换歌曲，换图片，震动……）
                onVibrator();
            }
        });
    }

    public void shakeMain(View v) {
        switch (v.getId()) {
            case R.id.shake_back_button:
       //         onBackPressed();
                onVibrator();
                break;
            case R.id.shakeMain:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } break;
            case R.id.shake_imageButton:
/*                intent = new Intent(this,ShakeSettingsActivity.class);
                startActivity(intent);*/
                break;
        }
    }

    private void onVibrator() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        MediaPlayer shakeEffect = MediaPlayer.create(this, R.raw.shake_effect); //  获取资源
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);  // 获取振动器Vibrator实例
        if (vibrator == null) {
            Vibrator localVibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator = localVibrator;
        }
        vibrator.vibrate(500L);
        shakeEffect.start();    
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                searchFriend(); //  搜索同时参与摇一摇的好友
            }
        }, 1000);
    }

    private void searchFriend() {

        //  TODO 上传Acccount、时间节点
        //  TODO 获取头像、昵称、性别、
        //创建我们的进度条
        progressDialog.setMessage("正在搜寻同一时刻摇晃手机的人");
        progressDialog.onStart();
        progressDialog.show();

       /*
        if(NetworkService.getInstance().getIsConnected()) {
            String userInfo = "type"+"-"+Integer.toString(GlobalMsgUtils.msgShake)+"-"+"account"+"-"+"wind";
            Log.v("aaaaa", userInfo);
            NetworkService.getInstance().sendUpload(userInfo);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Toast.makeText(ShakeActivity.this, "服务器连接失败~(≧▽≦)~啦啦啦", Toast.LENGTH_SHORT).show();
            Log.v("Login", "已经执行T（）方法");
        }
        */
/*        boolean flag = false;
        flag = pop();
        while (true) {
            if (flag) {
                System.out.println("#### AA");
                progressDialog.dismiss();
                System.out.println("#### BB");
                popupWindow.setAnimationStyle(R.style.MyDialogStyleBottom);
                popupWindow.showAsDropDown(findViewById(R.id.shake_image));
                System.out.println("#### CC");
                break;
            }
        }*/
    }
    
    public void ShakePopup(View view) {
        switch (view.getId()) {
            case R.id.shake_friend_info:
                Intent intent1 = new Intent(ShakeActivity.this,WelcomeActivity.class);
                startActivity(intent1);
                this.finish();
                break;
            case R.id.userPhoto:
/*                Intent intent2 = new Intent(ShakeActivity.this,PortraitPopWin.class);
                intent2.putExtra("DRAWABLE",this.portrait.getDrawingCache());
                startActivity(intent2);*/
                break;
        }
    }
    
    private boolean pop() {
        this.userName.setText("我是小涛啊");
/*        Drawable portraitDrawable = getResources().getDrawable(R.drawable.mypic);*/

//        Bitmap bitmap = ChangeUtils.toBitmap(this.str);
//        Drawable portraitDrawable = new BitmapDrawable(bitmap);
//        this.portrait.setImageBitmap(bitmap);
/*        this.portrait.setImageDrawable(portraitDrawable);*/
        
        Drawable drawable = getResources().getDrawable(R.drawable.man);
        this.sex.setImageDrawable(drawable);
        return true;
    }

    private void popInitView(Intent intent) {
        Drawable sexDrawable = null;
        this.portrait.setImageBitmap(ChangeUtils.toBitmap(intent.getStringExtra("rePhoto")));
/*        Bitmap bm = ChangeUtils.toBitmap(intent.getStringExtra("rePhoto")); //xxx根据你的情况获取   
        BitmapDrawable portraitDrawable = new BitmapDrawable(getResource(), bm);*/
/*        Drawable portraitDrawable = new BitmapDrawable(ChangeUtils.toBitmap(intent.getStringExtra("rePhoto")));
        this.portrait.setImageDrawable(portraitDrawable);*/
        this.userName.setText(intent.getStringExtra("reAccount"));
        if (intent.getStringExtra("reGender").equals("男")) {
            sexDrawable = getResources().getDrawable(R.drawable.man);
        } else {
            sexDrawable = getResources().getDrawable(R.drawable.women);
        }
        this.sex.setImageDrawable(sexDrawable);
    }

    private void registerBroadcas() {
        //动态方式注册广播接收者
        this.receiver = new ShakeBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.decipherstranger.SHAKE");
        this.registerReceiver(receiver, filter);
    }

    public class ShakeBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.android.decipherstranger.SHAKE")) {
                popInitView(intent);
                MyStatic.friendAccount = intent.getStringExtra("reAccount");
                progressDialog.dismiss();
                popupWindow.setAnimationStyle(R.style.MyDialogStyleBottom);
                popupWindow.showAsDropDown(findViewById(R.id.shake_image));
            }
        }
    }

}