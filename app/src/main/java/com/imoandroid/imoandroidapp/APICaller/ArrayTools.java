package com.imoandroid.imoandroidapp.APICaller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.ArrayList;



/* Modified version of https://gist.github.com/gbersac/8666ad9a7682d6d865ce
 *
 *
 * */
public class ArrayTools
{
    private static class ValueComparator<K , V extends Comparable<V>> implements Comparator<K>
    {
        Map<K, V> map;

        public ValueComparator(Map<K, V> map) {
            this.map = map;
        }

        @Override
        public int compare(K keyA, K keyB) {

            Comparable<V> valueA = (Comparable<V>)toObject(map.get(keyA).getClass(),map.get(keyA).toString());
//            ClassLoader cl;
//            Class c = cl.loadClass(name);

            V valueB = (V)toObject(map.get(keyB).getClass(),map.get(keyB).toString());

            System.out.println("valueA:" + valueA + " valueB:" + valueB + " result: " + valueA.compareTo(valueB));
            System.out.println("typeA:" + valueA.getClass() + " typeB:" + valueB.getClass());
            return valueA.compareTo(valueB);
        }

    }

    public static<K, V extends Comparable<V>> Map<K, V> sortByValue(Map<K, V> unsortedMap)
    {
        Map<K, V> sortedMap = new
                TreeMap<K, V>(new ValueComparator<K, V>(unsortedMap));
        sortedMap.putAll(unsortedMap);
        return sortedMap;
    }

    public static Object toObject( Class clazz, String value ) {
        if( Boolean.class == clazz ) return Boolean.parseBoolean( value );
        if( Byte.class == clazz ) return Byte.parseByte( value );
        if( Short.class == clazz ) return Short.parseShort( value );
        if( Integer.class == clazz ) return Integer.parseInt( value );
        if( Long.class == clazz ) return Long.parseLong( value );
        if( Float.class == clazz ) return Float.parseFloat( value );
        if( Double.class == clazz ) return Double.parseDouble( value );
        return value;
    }

    public static Map sortShit(ArrayList<Object> obj, String sortBy){

        Map items = new HashMap();
        Map official = new HashMap();
        for (Object item : obj){
            items.put((String)((Map)item).get("code"), ((Map)item).get(sortBy));
            official.put((String)((Map)item).get("code"), (LinkedHashMap) item);
        }

        Map sortedMap = sortByValue(items);

        LinkedHashMap back = new LinkedHashMap();
        for (Object k : sortedMap.keySet()){
            back.put(k,official.get((String)k));
        }


        return back;
    }


}