package com.adam.programList.list2_5;

import java.util.Set;
import java.util.TreeSet;

/**
 * Annotation:
 * 不可变类（不可变对象）Immutable Object
 * 尽管集合是可变的，但是这个类的设计保证构造函数在退出之后
 * 集合不会再被改变。
 * 通过生命 planets 为 final，这个属性的引用不能被改变，而且
 * 该引用也不能被缓存，所以缓存变量的问题也不复存在。
 *
 * Java 能让你安全的访问 final 属性而无需同步。
 *
 * @Author: Adam Ming
 * @Date: Mar 11, 2019 at 8:37:03 PM
 */
public final class Planets {
    private final Set<String> planets = new TreeSet<>();

    public Planets() {
        planets.add("Mercury");
        planets.add("Venus");
        planets.add("Earth");
        planets.add("Mars");
        planets.add("Jupiter");
        planets.add("Saturn");
        planets.add("Uranus");
        planets.add("Neptune");
    }

    public boolean isPlanet(String planetName) {
        return planets.contains(planetName);
    }
}
