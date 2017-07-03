package com.example.admin.puzzles;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

public class Field {

    private int width, height;
    private ArrayList<Puzzle> puzzles;

    public class Puzzle {
        public int N;

        public Puzzle(int N) {
            this.N = N;
        }
        public String toString() {
            return "Puzzle[" + N + "]";
        }
    }

    public Field(int width, int height) {
        setSize(width, height);
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getPuzzlesCount() {
        return puzzles.size();
    }
    public Puzzle getPuzzle(int i) {
        return puzzles.get(i);
    }
    public Puzzle getPuzzle(int x, int y) {
        return puzzles.get(y*width + x);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        puzzles = new ArrayList<>();
        for (int i = 0; i < width*height; i++)
            puzzles.add(new Puzzle(i));
    }
    public void swap(Point first, Point second) {
        int firstN = first.y*width + first.x;
        int secondN = second.y*height + second.x;
        Puzzle tmp = puzzles.get(firstN);
        puzzles.set(firstN, puzzles.get(secondN));
        puzzles.set(secondN, tmp);
    }
    public void mix() {
        ArrayList<Puzzle> newPuzzles = new ArrayList<>();
        Random rnd = new Random();
        while (!puzzles.isEmpty()) {
            int index = rnd.nextInt(puzzles.size());
            newPuzzles.add(puzzles.get(index));
            puzzles.remove(index);
        }
        puzzles = newPuzzles;
    }
    public boolean check() {
        for (int i = 0; i < puzzles.size(); i++)
            if (puzzles.get(i).N != i) return false;
        return true;
    }
}