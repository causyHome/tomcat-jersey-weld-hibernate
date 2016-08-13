package com.causy;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        System.out.println(Stream.of("1", "2", "3").map(Integer::parseInt).collect(Collectors.toList()));
        System.out.println( "Hello World!" );
    }
}
