package com.chao.bookviki.util;

import com.chao.bookviki.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2017/5/8.
 */

public class JingXuanDiverdedUtil {
    /**
     * 先随机生成每个type 需要的数量
     * 分别去获取已经用到的每个type 各20个
     * 各个type20 减去将要用到的数量
     *
     * 下次
     * 先随机生成每个type 需要的数量
     * 看下够不够
     * 不够继续获取20个
     */
    private static Random random = new Random();
    public static final Map<String,String> map;
    public static final Map<String,Integer> iconMap;
    private static final List<String> list;
    public static  Map<Integer,TypeObj> type2PageMap;
    static {
        map = new HashMap<>();
        map.put("war","军事");
        map.put("sport","体育");
        map.put("tech","科技");
        map.put("edu","教育");
        map.put("ent","娱乐");
        map.put("money","财经");
        map.put("gupiao","股票");
        map.put("travel","旅游");
        map.put("lady","女人");
        list = new ArrayList<>(map.keySet());
        type2PageMap = new HashMap<>(map.size());
        int i = 0;
        for (Map.Entry<String,String> entry : map.entrySet()){
            String type = entry.getKey();
            TypeObj obj = new TypeObj(type,1);
            type2PageMap.put(i,obj);
            i++;
        }
        iconMap = new HashMap<>(map.size());
        iconMap.put("war", R.mipmap.junshi);
        iconMap.put("sport",R.mipmap.tiyu);
        iconMap.put("tech",R.mipmap.keji);
        iconMap.put("edu",R.mipmap.jiaoyu);
        iconMap.put("ent",R.mipmap.yule);
        iconMap.put("money",R.mipmap.caijing);
        iconMap.put("gupiao",R.mipmap.gupiao);
        iconMap.put("travel",R.mipmap.lvyou);
        iconMap.put("lady",R.mipmap.nvren);

    }

   public static class TypeObj{
        public String type;
        public int currentPage;

        public TypeObj(String type, int currentPage) {
            this.type = type;
            this.currentPage = currentPage;
        }
    }

    public static TypeObj getRanDomType(){
        int index = random.nextInt(type2PageMap.size());
        TypeObj temp  = type2PageMap.get(index);
        return temp;
    }

    public static Map<String, Integer> diverided(int limit){
        int size = list.size();//大小，多少个分类
        Map<String,Integer> type2NumMap = new HashMap<>();
        int totalNum = 0;
        for (int i = 0;i< limit;i++){
            if (totalNum < limit){
            //随机生成大小为Limt 里面包含随机的分类
            int randomIndex = random.nextInt(size);
            String type = list.get(randomIndex);
            int randomNum = -1;
                randomNum = random.nextInt(limit-totalNum)+1;
                //随机生成个大小
                Integer num = type2NumMap.get(type);
                if (num == null){
                    type2NumMap.put(type,randomNum);
                }else {
                    type2NumMap.put(type,randomNum+num);
                }
                totalNum+=randomNum;
            }else {
                break;
            }
        }
        return type2NumMap;
    }

    /**
     *
     * @param toUseNum
     * @param limit
     * @param currentNum
     * @return
     */
    public static int calculateCurrentPage(int currentPage,int toUseNum,int limit,int currentNum){
        int num = toUseNum + currentNum;
        if (num >limit){
            return  currentPage+1;
        }else {
            return  currentPage;
        }
    }
}
