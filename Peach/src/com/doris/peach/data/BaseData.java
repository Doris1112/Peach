package com.doris.peach.data;

import java.util.ArrayList;
import java.util.List;

import com.doris.peach.database.DBUtil;
import com.doris.peachlibrary.domain.District;
import com.doris.peachlibrary.util.animation.SwitchAnimationUtil.AnimationType;

/**
 * 
 * @author Doris
 *
 * 2016年4月13日
 */
public class BaseData {
	
	public static final String TB_LIST = "tb_list";
	public static final String LIST_ID = "list_id";
	public static final String LIST_ITEM = "list_item";
	
	public static final String TB_DISTRICT = "tb_district";
	public static final String DISTRICT_ID = "district_id";
	public static final String DISTRICT_ITEM = "district_item";
	public static final String DISTRICT_FLAG = "district_flag";
	
	public static final String TB_TELEPHONE_INTERCEPT = "tb_telephone_intercept";
	public static final String TELEPHONE_ID = "telephone_id";
	public static final String TELEPHONE_DATE = "telephone_date";
	public static final String  TELEPHONE_INFO = "telephone_info";
	public static final String  TELEPHONE_INTERCEPT_COUNT = "telephone_intercept_count";
	
	public static final String TB_NOTE = "tb_note";
	public static final String NOTE_ID = "note_id";
	public static final String NOTE_CONTENT = "note_content";
	public static final String NOTE_TIME = "note_time";
	public static final String NOTE_BG_INDEX = "note_bg_index";
	
	public static List<String[]> list = new ArrayList<String[]>();
	public static void getLists(DBUtil dbUtil){
		BaseData.list.clear();
		BaseData.list.addAll(dbUtil.getAllInfo(TB_LIST));
	}
	
	public static String[] item;
	
	public static List<District> provinces = new ArrayList<District>();
	public static void initProvinces(DBUtil dbUtil){
		BaseData.provinces.clear();
		List<String[]> list = dbUtil.query(TB_DISTRICT,
				new String[] { DISTRICT_ID, DISTRICT_ITEM }, 
				new String[] { DISTRICT_FLAG },
				new String[] { "0" });
		for (int i = 0; i < list.size(); i++) {
			BaseData.provinces.add(new District(list.get(i)[1], list.get(i)[0]));
		}
	}
	
	/**
	 * 城市列表
	 */
	public static final String CITYLIST = "http://apis.baidu.com/apistore/weatherservice/citylist";
	/**
	 * 历史7天和未来4天天气
	 */
	public static final String RECENTWEATHERS = "http://apis.baidu.com/apistore/weatherservice/recentweathers";
	/**
	 * 根据城市拼音
	 */
	public static final String WEATHER = "http://apis.baidu.com/apistore/weatherservice/weather";
	/**
	 * 根据城市名称
	 */
	public static final String CITYNAME = "http://apis.baidu.com/apistore/weatherservice/cityname";
	/**
	 * 根据城市代码
	 */
	public static final String CITYID = "http://apis.baidu.com/apistore/weatherservice/cityid";
	/**
	 * 城市信息列表
	 */
	public static final String CITYINFO = "http://apis.baidu.com/apistore/weatherservice/cityinfo";
	
	/**
	 * 页面控件展示动画
	 */
	public static AnimationType PAGE_VIEW_ANIM = AnimationType.SCALE;
	
	/**
	 * 状态栏高度
	 */
	public static int statusBarHeight = 0;
	
	/**
	 * 代码阅读
	 */
	public static final String CODE="#include<iostream>  \n" +
            "#include<queue>  \n" +
            "#include<string>  \n" +
            "#include<cstdio>  \n" +
            "#include<cstring>  \n" +
            "#include<vector>  \n" +
            "#define MAX 2005  \n" +
            "using namespace std;  \n" +
            "int in[MAX];  \n" +
            "vector<int>G[MAX];  \n" +
            "int TopSort(int n)  \n" +
            "{  \n" +
            "    queue<int>p;  \n" +
            "    int i, t, Count = 0;  \n" +
            "    for (i = 1; i <= n; i++)  \n" +
            "        if (in[i] == 0)  \n" +
            "            p.push(i);  \n" +
            "    while (!p.empty())  \n" +
            "    {  \n" +
            "        t = p.front(); p.pop();  \n" +
            "        Count++;  \n" +
            "        for (i = 0; i < G[t].size(); i++)  \n" +
            "        {  \n" +
            "            in[G[t][i]]--;  \n" +
            "            if (in[G[t][i]] == 0)  \n" +
            "                p.push(G[t][i]);  \n" +
            "        }  \n" +
            "    }  \n" +
            "    if (Count <n)  \n" +
            "        return 1;  \n" +
            "    else return 0;  \n" +
            "  \n" +
            "}  \n" +
            "int main()  \n" +
            "{  \n" +
            "    int n, t, i, j, cc = 0;  \n" +
            "    cin.sync_with_stdio(false);  \n" +
            "    string s;  \n" +
            "    cin >> t;  \n" +
            "    while (t--)  \n" +
            "    {      \n" +
            "        cc++;  \n" +
            "        cin >> n;  \n" +
            "        memset(in, 0, sizeof(in));  \n" +
            "        for (int i = 0; i <= n; i++) G[i].clear();  \n" +
            "        for (i = 1; i <= n; i++)  \n" +
            "        {  \n" +
            "            cin >> s;  \n" +
            "            for (j = 0; j < s.size(); j++)  \n" +
            "            {  \n" +
            "                if (s[j] == '1')  \n" +
            "                {  \n" +
            "                    G[i].push_back(j + 1);  \n" +
            "                    in[j+1]++;  \n" +
            "  \n" +
            "                }  \n" +
            "            }  \n" +
            "        }  \n" +
            "        if (TopSort(n))  \n" +
            "            printf(\"Case #%d: Yes\\n\", cc);  \n" +
            "        else printf(\"Case #%d: No\\n\", cc);  \n" +
            "    }  \n" +
            "    return 0;  \n" +
            "}  ";
	
	/**
	 * 代码阅读HTML
	 */
    public static final String HTML="<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "\t<title></title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1>This is a code</h1>\n" +
            "<pre class=\"code\">\n" +
            "#include<stdio.h>\n" +
            "int main()\n" +
            "{\n" +
            "\tprintf(\"hello world\");\n" +
            "}\n" +
            "</pre>\n" +
            "<h1>This is another code</h1>\n" +
            "<pre class=\"code\">\n" +
            "class Main\n" +
            "{\n" +
            "\tprivate String name;\n" +
            "\tMain(String name){\n" +
            "\t\tthis.name=name;\n" +
            "\t}\n" +
            "\tpublic static void main(String []args){\n" +
            "\t\tMain main=new Main(\"hello\");\n" +
            "\t\tSystem.out.println(\"init\");\n" +
            "\t}\n" +
            "}\n" +
            "</pre>\n" +
            "</body>\n" +
            "</html>";
}
