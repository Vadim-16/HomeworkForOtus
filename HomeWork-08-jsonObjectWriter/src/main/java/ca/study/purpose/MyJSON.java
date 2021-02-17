package ca.study.purpose;

import com.google.gson.Gson;

import javax.json.JsonString;
import java.util.ArrayList;
import java.util.List;


public class MyJSON {

    public static void main(String[] args) {
        SomeClass someClass = new SomeClass();

    }

    public JsonString toMyJson(Object obj){

        return null;
    }

    static class SomeClass{
        private long[] numbers;
        private Object[] objects;
        private List<String> list;

        public SomeClass() {
            numbers = new long[]{1,2,3,4,5};
            objects = new Object[]{new Object(), new Object()};
            list = new ArrayList<>();
            list.add("One");
            list.add("two");
            list.add("three");
        }

        public long[] getNumbers() {
            return numbers;
        }

        public void setNumbers(long[] numbers) {
            this.numbers = numbers;
        }

        public Object[] getObjects() {
            return objects;
        }

        public void setObjects(Object[] objects) {
            this.objects = objects;
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }
}
