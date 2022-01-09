package main;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Nodelink implements Comparable<Nodelink> {
    private final String url;
    private final Nodelink parent;
    private final int depth;
    private Set<Nodelink> subLinks = ConcurrentHashMap.newKeySet();

    public Nodelink(String url) {
        this.url = url;
        this.depth = 0;
        this.parent = null;
    }

    public Nodelink(String url, Nodelink parent) {
        this.url = url;
        this.depth = parent.getDepth() + 1;
        this.parent = parent;
    }

    public synchronized void addSubLink(Nodelink subLink) {
        this.subLinks.add(subLink);
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nodelink node = (Nodelink) o;

        return Objects.equals(url, node.url);
    }
    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, this.getDepth())));
        return result.append(this.getUrl()).toString();
    }
}