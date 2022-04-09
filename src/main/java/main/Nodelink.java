package main;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
/**Класс описывающий узловые ссылки сайта
 * @autor VG
 * @version 0.1
 * **/
public class Nodelink implements Comparable<Nodelink> {
    /**Поле адреса страницы*/
    private final String url;
    /**Поле родительского объекта NodeLink*/
    private final Nodelink parent;
    /**Поле "глубины" ссылки относительно главной страницы*/
    private final int depth;
    /**Полесписок ссылок содержащихся на страницу*/
    private Set<Nodelink> subLinks = ConcurrentHashMap.newKeySet();

    /**Конструктор класса NodeLink
     * @param url - адресс страницы*/
    public Nodelink(String url) {
        this.url = url;
        this.depth = 0;
        this.parent = null;
    }
    /**Конструктор класса NodeLink
     * @param url - адресс страницы
     * @param parent - обект родитель */
    public Nodelink(String url, Nodelink parent) {
        this.url = url;
        this.depth = parent.getDepth() + 1;
        this.parent = parent;
    }
    /**Функция добавления сслыки содержищейся на странице в список
     * @param subLink - ссылка содержащаяся на странице*/
    public synchronized void addSubLink(Nodelink subLink) {
        this.subLinks.add(subLink);
    }
    /**Getters and setters*/
    public Set<Nodelink> getSubLinks() {
        return subLinks;
    }

    public Nodelink getParent() {
        return parent;
    }

    public int getDepth() {
        return depth;
    }

    public String getUrl() {
        return url;
    }

    @Override
    /**функция сравнения "глубины" ссылки*
     * @param node - объект класса  NodeLink
     * @return -1  если NodeLink#parent = null, node == NodeLink
     * NodeLink#deth > node$deth
     * 1 если node#parent = null, NodeLink#parent == node#parent
     *      * NodeLink#deth < node$deth
     */
    public int compareTo(Nodelink node) {
        if (this.getParent() == null) return -1;
        if (node.getParent() == null) return 1;
        if (node.getParent().equals(this)) return -1;
        if (this.getParent().equals(node)) return 1;
        if (this.getDepth() == node.getDepth()) {
            if (this.getParent().equals(node.getParent())) {
                return this.getUrl().compareTo(node.getUrl());
            } else {
                return this.getParent().compareTo(node.getParent());
            }
        } else {
            return (this.getDepth() > node.getDepth()) ? this.getParent().compareTo(node) : this.compareTo(node.getParent());
        }
    }
    @Override
    /**Функция определния  равенства ссылок
     * @param o - объект сравнения с текущим объектов
     * @return true если ссылки равны
     * false если не равны*/
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nodelink node = (Nodelink) o;

        return Objects.equals(url, node.url);
    }
    @Override
    /**Функция определнрия hachcode*/
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }
    @Override
    /**Функция вывода в строку объекта NodeLink*/
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, this.getDepth())));
        return result.append(this.getUrl()).toString();
    }
}