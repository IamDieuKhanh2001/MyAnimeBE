package com.hcmute.myanime.common;

import java.util.List;

public class Common
{
    public static class MessageRespone
    {
        // Movie
        public static String StorageMovieSuccess = "Storage movie success";
        public static String StorageMovieFail = "Storage movie fail";
        public static String UpdateMovieSuccess = "Update movie success";
    }

    public static class Function
    {
        public static <T> void revlist(List<T> list)
        {
            // base condition when the list size is 0
            if (list.size() <= 1 || list == null)
                return;


            T value = list.remove(0);

            // call the recursive function to reverse
            // the list after removing the first element
            revlist(list);

            // now after the rest of the list has been
            // reversed by the upper recursive call,
            // add the first value at the end
            list.add(value);
        }
    }
}
