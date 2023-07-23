import java.util.*;

public class Sorts {
    public interface Observer {
        public void updateScreen(int[] arr, int i, int j, int comp, int swap, int access, int space);

        void displaySortedArray();
    }

    int[] arr;
    int first = -1;
    int second = -1;
    int comparisons = 0;
    int swaps = 0;
    int access = 0;
    int space = 0;
    ArrayList<Observer> observers = new ArrayList<Observer>();
    public static volatile int delay = 2;
    public static boolean keepSorting = true;

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.updateScreen(this.arr, this.first, this.second, this.comparisons, this.swaps, this.access,
                    this.space);
        }
    }

    public void notifyObserversDone() {
        for (Observer observer : this.observers) {
            observer.displaySortedArray();
            ;
        }
    }

    public void sort(int[] a, String method) {
        int n = a.length;
        int s = 0;
        int e = n - 1;
        this.arr = a.clone();
        if (method.equals("bubble")) {
            bubble_sort(arr);
        } else if (method.equals("selection")) {
            selection_sort(arr);
        } else if (method.equals("insertion")) {
            insertion_sort(arr, n);
        } else if (method.equals("merge")) {
            merge_sort(arr, s, e);
        } else if (method.equals("quick")) {
            quick_sort(arr, s, e);
        } else if (method.equals("shell")) {
            shell_sort(arr, n);
        } else if (method.equals("count")) {
            count_sort(arr, n);
        } else if (method.equals("radix")) {
            radix_sort(arr, n);
        } else if (method.equals("heap")) {
            heap_sort(arr, 0, n - 1);
        } else if (method.equals("bingo")) {
            bingo_sort(arr);
        } else if (method.equals("tim")) {
            tim_sort(arr);
        } else if (method.equals("comb")) {
            comb_sort(arr);
        } else if (method.equals("pigeonhole")) {
            pigeonhole_sort(arr);
        } else if (method.equals("bucket")) {
            bucket_sort(arr);
        } else if (method.equals("cycle")) {
            cycle_sort(arr);
        } else if (method.equals("cocktail")) {
            cocktail_sort(arr);
        } else if (method.equals("bitonic")) {
            bitonic_sort(arr);
        } else if (method.equals("pancake")) {
            pancake_sort(arr);
        } else if (method.equals("bogo")) {
            bogo_sort(arr);
        } else if (method.equals("gnome")) {
            gnome_sort(arr);
        } else if (method.equals("stooge")) {
            stooge_sort(arr, 0, n);
        } else if (method.equals("oddeven")) {
            oddeven_sort(arr);
        } else if (method.equals("intro")) {
            intro_sort(arr);
        }
        this.comparisons = 0;
        this.swaps = 0;
        this.access = 0;
        this.space = 0;
        if (keepSorting)
            notifyObserversDone();
    }

    public static void delay() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void swap(int a, int b, int[] arr) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    private int max(int arr[], int n) {
        int maxi = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            if (maxi < arr[i]) {
                maxi = arr[i];
            }
        }
        return maxi;
    }

    private int min(int arr[], int start, int n) {
        int mini = Integer.MAX_VALUE;
        for (int i = start; i < n; i++) {
            if (mini > arr[i]) {
                mini = arr[i];
            }
        }
        return mini;
    }

    private void bubble_sort(int[] arr) {
        int n = arr.length;
        space++;
        for (int i = 0; i < n; i++) {
            int counter = 0;
            space++;
            for (int j = 0; j < n - i - 1; j++) {
                if (keepSorting == false)
                    return;
                comparisons++;
                access++;
                access++;
                if (arr[j] > arr[j + 1]) {
                    this.first = j;
                    this.second = j + 1;
                    notifyObservers();
                    delay();
                    swap(j, j + 1, arr);
                    swaps++;
                    notifyObservers();
                    delay();
                    counter++;
                }
            }
            if (counter == 0) {
                break;
            }
        }
    }

    private void selection_sort(int[] arr) {
        int n = arr.length;
        space++;
        for (int i = 0; i < n - 1; i++) {
            int min = Integer.MAX_VALUE, idx = 0;
            space += 2;
            for (int j = i; j < n; j++) {
                if (keepSorting == false)
                    return;
                comparisons++;
                access++;
                if (min > arr[j]) {
                    this.first = j;
                    notifyObservers();
                    delay();
                    min = arr[j];
                    idx = j;
                }
            }
            if (idx != i) {
                this.first = i;
                this.second = idx;
                notifyObservers();
                delay();
                swap(i, idx, arr);
                swaps++;
                notifyObservers();
                delay();
            }
        }
    }

    private void insertion_sort(int[] arr, int n) {
        for (int i = 1; i < n; i++) {
            int j = i;
            space++;
            while (j != 0) {
                if (keepSorting == false)
                    return;
                comparisons++;
                access++;
                access++;
                if (arr[j] < arr[j - 1]) {
                    this.first = j;
                    this.second = j - 1;
                    notifyObservers();
                    delay();
                    swap(j, j - 1, arr);
                    swaps++;
                    notifyObservers();
                    delay();
                }
                j--;
            }
        }
    }

    private void merge(int arr[], int s, int e) {
        int m = s + (e - s) / 2;
        int n1 = m - s + 1;
        int n2 = e - m;
        space += 3;
        int[] arr1 = new int[n1], arr2 = new int[n2];
        space += n1 + n2;
        for (int i = 0; i < n1; i++) {
            access++;
            arr1[i] = arr[i + s];
        }
        for (int j = 0; j < n2; j++) {
            access++;
            arr2[j] = arr[j + m + 1];
        }
        int i = 0, j = 0, k = s;
        while (i < n1 && j < n2) {
            if (keepSorting == false)
                return;
            comparisons++;
            access++;
            access++;
            if (arr1[i] <= arr2[j]) {
                this.first = k;
                this.second = i + s;
                notifyObservers();
                delay();
                access++;
                arr[k++] = arr1[i++];
                notifyObservers();
                delay();
            } else {
                this.first = k;
                this.second = j + m + 1;
                notifyObservers();
                delay();
                access++;
                arr[k++] = arr2[j++];
                notifyObservers();
                delay();
            }
        }
        while (i < n1) {
            if (keepSorting == false)
                return;
            this.first = k;
            this.second = i + s;
            notifyObservers();
            delay();
            access++;
            arr[k++] = arr1[i++];
            notifyObservers();
            delay();
        }
        while (j < n2) {
            if (keepSorting == false)
                return;
            this.first = k;
            this.second = j + m + 1;
            notifyObservers();
            delay();
            access++;
            arr[k++] = arr2[j++];
            notifyObservers();
            delay();
        }
    }

    private void merge_sort(int[] arr, int s, int e) {
        if (s < e) {
            if (keepSorting == false)
                return;
            int m = s + (e - s) / 2;
            space++;
            merge_sort(arr, s, m);
            merge_sort(arr, m + 1, e);
            merge(arr, s, e);
        }
    }

    private int partition(int s, int e, int arr[]) {
        int pivot = arr[e];
        int p = s;
        space += 2;
        for (int i = s; i < e; i++) {
            if (keepSorting == false)
                return 1;
            comparisons++;
            access++;
            if (arr[i] <= pivot) {
                this.first = i;
                this.second = p;
                notifyObservers();
                delay();
                swap(i, p, arr);
                swaps++;
                notifyObservers();
                delay();
                p++;
            }
        }
        this.first = p;
        this.second = e;
        notifyObservers();
        delay();
        swap(p, e, arr);
        swaps++;
        notifyObservers();
        delay();
        return p;
    }

    private void quick_sort(int arr[], int l, int h) {
        if (l < h) {
            if (keepSorting == false)
                return;
            int j = partition(l, h, arr);
            quick_sort(arr, l, j - 1);
            quick_sort(arr, j + 1, h);
        }
    }

    private void shell_sort(int arr[], int n) {
        int gap = n / 2;
        space++;
        while (gap >= 1) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j = i;
                space += 2;
                while (j >= gap && arr[j - gap] > temp) {
                    if (keepSorting == false)
                        return;
                    comparisons++;
                    this.first = j;
                    this.second = j - gap;
                    access++;
                    arr[j] = arr[j - gap];
                    swaps++;
                    notifyObservers();
                    delay();
                    j -= gap;
                }
                access++;
                arr[j] = temp;
                notifyObservers();
                delay();
            }
            gap /= 2;
        }
    }

    private void count_sort(int[] a, int n) {
        int max = max(a, n);
        int min = min(a, 0, n);
        int range = max - min + 1;
        int[] count = new int[range], b = new int[n];
        Arrays.fill(count, 0);
        for (int i = 0; i < n; i++) {
            count[a[i] - min]++;
        }
        for (int i = 1; i < range; i++) {
            count[i] += count[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            if (keepSorting == false)
                return;
            this.first = --count[a[i] - min];
            this.second = i;
            notifyObservers();
            delay();
            b[this.first] = a[i];
            notifyObservers();
            delay();
        }
        for (int i = 0; i < n; i++) {
            if (keepSorting == false)
                return;
            a[i] = b[i];
            notifyObservers();
            delay();
        }
    }

    private void radix_count_sort(int a[], int n, int exp) {
        int[] count = new int[19], b = new int[n];
        space += n + 19;
        Arrays.fill(count, 0);
        for (int i = 0; i < n; i++) {
            access++;
            count[9 + (a[i] / exp) % 10]++;
        }
        for (int i = 1; i < 19; i++) {
            count[i] += count[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            if (keepSorting == false)
                return;
            comparisons++;
            this.first = --count[9 + (a[i] / exp) % 10];
            this.second = i;
            notifyObservers();
            delay();
            access++;
            b[this.first] = a[i];
            notifyObservers();
            delay();

        }
        for (int i = 0; i < n; i++) {
            if (keepSorting == false)
                return;
            access++;
            a[i] = b[i];
            notifyObservers();
            delay();

        }
    }

    private void radix_sort(int arr[], int n) {
        int max = max(arr, n);
        int min = min(arr, 0, n);
        int maxi = Math.max(Math.abs(max), Math.abs(min));
        space += 3;
        for (int exp = 1; maxi / exp > 0; exp *= 10) {
            if (keepSorting == false)
                return;
            radix_count_sort(arr, n, exp);
        }
    }

    private void bingo_sort(int arr[]) {
        int n = arr.length;
        int start = 0;
        int max = max(arr, n);
        while (start < n) {
            if (keepSorting == false)
                return;
            int bingo = min(arr, start, n);
            for (int i = start; i < n; i++) {
                if (arr[i] == bingo) {
                    this.first = start;
                    this.second = i;
                    notifyObservers();
                    delay();
                    swap(start++, i, arr);
                    notifyObservers();
                    delay();
                }
            }
            if (bingo == max)
                break;
        }
    }

    private void heapify(int arr[], int idx, int n) {
        if (keepSorting == false)
            return;
        int r = idx * 2 + 2;
        int l = idx * 2 + 1;
        space += 2;
        int max = idx;
        if (l >= n) {
            return;
        } else if (l == n - 1) {
            max = l;
        } else {
            comparisons += 2;
            access++;
            access++;
            if (arr[l] > arr[r]) {
                max = l;
            } else {
                max = r;
            }
        }
        access++;
        if (max != idx && arr[max] > arr[idx]) {
            if (keepSorting == false)
                return;
            this.first = max;
            this.second = idx;
            swap(max, idx, arr);
            swaps++;
            notifyObservers();
            delay();
            heapify(arr, max, n);
        }
    }

    private void heap_sort(int arr[], int s, int e) {
        int n = e + 1;
        space++;
        for (int i = (n / 2) - 1; i >= 0; i--) {
            if (keepSorting == false)
                return;
            heapify(arr, i, n);
        }
        for (int j = n - 1; j >= 0; j--) {
            if (keepSorting == false)
                return;
            this.first = 0;
            this.second = j;
            swap(0, j, arr);
            notifyObservers();
            delay();
            heapify(arr, 0, j);
        }
    }

    private void comb_sort(int[] arr) {
        int n = arr.length;
        space++;
        int gap = n;
        boolean swapped = true;
        space += 2;
        while (gap > 1 || swapped) {
            gap = (int) (gap / 1.3);
            if (gap < 1) {
                gap = 1;
            }
            swapped = false;
            for (int i = 0; i + gap < n; i++) {
                if (!keepSorting)
                    return;
                comparisons++;
                access++;
                access++;
                if (arr[i] > arr[i + gap]) {
                    this.first = i;
                    this.second = i + gap;
                    notifyObservers();
                    delay();
                    swap(i, i + gap, arr);
                    swaps++;
                    swapped = true;
                    notifyObservers();
                    delay();
                }
            }
        }
    }

    private void insertion_sort(int[] arr, int s, int e) {
        for (int i = 1; i <= e; i++) {
            if (i != e && arr[i] > arr[i + 1]) {
                swap(i, i + 1, arr);
            }
            int j = i;
            while (j != 0) {
                if (keepSorting == false)
                    return;
                if (arr[j] < arr[j - 1]) {
                    swap(j, j - 1, arr);
                }
                j--;
            }
        }
    }

    private void merge(int[] arr, int s1, int e1, int s2, int e2) {
        int i = s1, j = s2, k = s1;
        while (i <= e1 && j <= e2) {
            if (keepSorting == false)
                return;
            if (arr[i] <= arr[j]) {
                arr[k++] = arr[i++];
            } else {
                arr[k++] = arr[j++];

            }
        }
        while (i <= e1) {
            if (keepSorting == false)
                return;
            arr[k++] = arr[i++];
        }
        while (j <= e2) {
            if (keepSorting == false)
                return;
            arr[k++] = arr[j++];
        }
    }

    private int runSize(int n) {
        int r = 0;
        while (n >= 64) {
            r |= n & 1;
            n >>= 1;
        }
        return n + r;
    }

    private void tim_sort(int[] arr) {
        int n = arr.length;
        int runSize = runSize(n);
        for (int i = 0; i < n; i += runSize) {
            if (!keepSorting)
                return;
            int start = i;
            int end = Math.min(start + runSize - 1, n - 1);
            this.first = start;
            this.second = end;
            notifyObservers();
            delay();
            insertion_sort(arr, start, end);
            notifyObservers();
            delay();
        }
        while (runSize < n) {
            if (!keepSorting)
                return;
            for (int i = 0; i < n - runSize; i += 2 * runSize) {
                int start = i;
                int mid = start + runSize - 1;
                int end = Math.min(start + 2 * runSize - 1, n - 1);
                this.first = start;
                this.second = end;
                notifyObservers();
                delay();
                merge(arr, start, mid, mid + 1, end);
                notifyObservers();
                delay();
            }
            runSize *= 2;
        }
    }

    private void pigeonhole_sort(int[] arr) {
        int n = arr.length;
        int min = min(arr, 0, n);
        int max = max(arr, n);
        int range = max - min + 1;
        space += 3;
        int[] count = new int[range];
        space += range;
        for (int i = 0; i < n; i++) {
            access++;
            count[arr[i] - min]++;
        }
        int index = 0;
        space++;
        for (int i = 0; i < range; i++) {
            while (count[i] > 0) {
                if (!keepSorting)
                    return;
                this.first = index;
                this.second = index + 1;
                notifyObservers();
                delay();
                access++;
                arr[index++] = i + min;
                count[i]--;
                notifyObservers();
                delay();
            }
        }
    }

    private void swap2(int i, int j, Integer[] arr) {
        Integer temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void heapify(Integer arr[], int idx, int n) {
        if (keepSorting == false)
            return;
        int r = idx * 2 + 2;
        int l = idx * 2 + 1;
        int max = idx;
        if (l >= n) {
            return;
        } else if (l == n - 1) {
            max = l;
        } else {
            if (arr[l] > arr[r]) {
                max = l;
            } else {
                max = r;
            }
        }
        if (max != idx && arr[max] > arr[idx]) {
            if (keepSorting == false)
                return;
            this.first = max;
            this.second = idx;
            swap2(max, idx, arr);
            notifyObservers();
            delay();
            heapify(arr, max, n);
        }
    }

    private void heap_sort(Integer arr[], int n) {
        for (int i = (n / 2) - 1; i >= 0; i--) {
            if (keepSorting == false)
                return;
            heapify(arr, i, n);
        }
        for (int j = n - 1; j >= 0; j--) {
            if (keepSorting == false)
                return;
            this.first = 0;
            this.second = j;
            swap2(0, j, arr);
            notifyObservers();
            delay();
            heapify(arr, 0, j);
        }
    }

    private void bucket_sort(int[] arr) {
        int n = arr.length;
        int maxVal = max(arr, n);
        int minVal = min(arr, 0, n);
        int range = maxVal - minVal + 1;
        int numBuckets = (int) Math.sqrt(range);
        ArrayList<List<Integer>> buckets = new ArrayList<List<Integer>>(numBuckets);
        for (int i = 0; i < numBuckets; i++) {
            buckets.add(new LinkedList<Integer>());
        }
        for (int i = 0; i < n; i++) {
            int bucketIndex = (int) ((arr[i] - minVal) * numBuckets / range);
            buckets.get(bucketIndex).add(arr[i]);
        }
        int counter = 0;
        for (List<Integer> list : buckets) {
            Integer[] t = list.toArray(new Integer[0]);
            heap_sort(t, t.length);
            for (Integer i : t) {
                arr[counter++] = i;
            }
        }
    }

    private void cycle_sort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int key = arr[i];
            int counter = i;
            space += 2;
            for (int j = i + 1; j < n; j++) {
                if (!keepSorting)
                    return;
                comparisons++;
                access++;
                if (arr[j] < key)
                    counter++;
            }
            if (counter == i)
                continue;
            while (key == arr[counter])
                counter++;
            if (key != arr[counter]) {
                this.first = counter;
                this.second = counter + 1;
                notifyObservers();
                delay();
                int temp = key;
                key = arr[counter];
                access++;
                arr[counter] = temp;
                swaps++;
                notifyObservers();
                delay();
            }
            while (counter != i) {
                counter = i;
                for (int j = i + 1; j < n; j++) {
                    if (!keepSorting)
                        return;
                    comparisons++;
                    access++;
                    if (arr[j] < key)
                        counter++;
                }
                while (key == arr[counter])
                    counter++;
                if (key != arr[counter]) {
                    this.first = counter;
                    this.second = counter + 1;
                    notifyObservers();
                    delay();
                    int temp = key;
                    key = arr[counter];
                    access++;
                    arr[counter] = temp;
                    swaps++;
                    notifyObservers();
                    delay();
                }
            }
        }
    }

    private void bitonic_sort(int[] arr) {
        int n = arr.length;
        space++;
        int[] padded = arr;
        space += padded.length;
        if ((n & n - 1) != 0) {
            int m = (int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2)));
            space++;
            padded = new int[m];
            space += m;
            System.arraycopy(arr, 0, padded, 0, n);
            for (int i = n; i < m; i++)
                padded[i] = Integer.MAX_VALUE;
            bitonic_rec(padded, 0, padded.length, true);
            System.arraycopy(padded, 0, arr, 0, n);

        } else
            bitonic_rec(arr, 0, padded.length, true);
    }

    private void bitonic_merge(int[] arr, int s, int count, boolean asc) {
        if (count <= 1)
            return;
        int mid = count / 2;
        space++;
        for (int i = s; i < s + mid; i++) {
            if (!keepSorting)
                return;
            comparisons++;
            access++;
            access++;
            if (asc == (arr[i] > arr[i + mid])) {
                this.first = i;
                this.second = i + mid;
                notifyObservers();
                delay();
                swap(i, i + mid, arr);
                swaps++;
                notifyObservers();
                delay();
            }
        }
        notifyObservers();
        delay();
        bitonic_merge(arr, s, mid, asc);
        bitonic_merge(arr, s + mid, mid, asc);
    }

    private void bitonic_rec(int[] arr, int s, int count, boolean asc) {
        if (count <= 1)
            return;
        int mid = count / 2;
        space++;
        bitonic_rec(arr, s, mid, true);
        notifyObservers();
        delay();
        bitonic_rec(arr, s + mid, mid, false);
        notifyObservers();
        delay();
        bitonic_merge(arr, s, count, asc);
    }

    private void pancake_sort(int[] arr) {
        int n = arr.length;
        space++;
        while (n > 0) {
            int max = Integer.MIN_VALUE;
            int idx = 0;
            space += 2;
            for (int j = 0; j < n; j++) {
                if (!keepSorting)
                    return;
                comparisons++;
                access++;
                if (max < arr[j]) {
                    max = arr[j];
                    idx = j;
                }
            }
            for (int j = 0; j <= idx / 2; j++) {
                this.first = idx - j;
                this.second = idx - j + 1;
                notifyObservers();
                delay();
                swap(j, idx - j, arr);
                swaps++;
                notifyObservers();
                delay();
            }
            for (int j = 0; j < n / 2; j++) {
                this.first = n - j - 1;
                this.second = n - j;
                notifyObservers();
                delay();
                swap(j, n - j - 1, arr);
                swaps++;
                notifyObservers();
                delay();
            }
            n--;
        }
    }

    private void bogo_sort(int[] arr) {
        while (!isSorted(arr)) {
            if (!keepSorting)
                return;
            shuffle(arr);
            notifyObservers();
            delay();
        }
    }

    private void shuffle(int[] arr) {
        int n = arr.length;
        space++;
        for (int i = 0; i < n; i++) {
            this.first = i;
            this.second = (int) (Math.random() * n);
            notifyObservers();
            delay();
            swap(i, this.second, arr);
            swaps++;
            if (!keepSorting)
                return;
            notifyObservers();
            delay();
        }
    }

    private boolean isSorted(int[] a) {
        int n = a.length;
        space++;
        for (int i = 0; i < n - 1; i++) {
            access += 2;
            if (a[i] > a[i + 1])
                return false;
        }
        return true;
    }

    private void cocktail_sort(int[] arr) {
        int n = arr.length;
        space++;
        for (int i = 0; i < n; i++) {
            boolean flag = false;
            space++;
            for (int j = i; j < n - i - 1; j++) {
                if (!keepSorting)
                    return;
                comparisons++;
                access++;
                access++;
                if (arr[j] > arr[j + 1]) {
                    this.first = j;
                    this.second = j + 1;
                    notifyObservers();
                    delay();
                    swap(j, j + 1, arr);
                    flag = true;
                    swaps++;
                    notifyObservers();
                    delay();
                }
            }
            for (int j = n - i - 1; j > i; j--) {
                if (!keepSorting)
                    return;
                comparisons++;
                access++;
                access++;
                if (arr[j] < arr[j - 1]) {
                    this.first = j;
                    this.second = j - 1;
                    notifyObservers();
                    delay();
                    swap(j, j - 1, arr);
                    flag = true;
                    swaps++;
                    notifyObservers();
                    delay();
                }
            }
            if (!flag)
                break;
        }
    }

    private void gnome_sort(int[] arr) {
        int n = arr.length;
        space++;
        int idx = 0;
        while (idx < n) {
            if (!keepSorting)
                return;
            if (idx == 0 || arr[idx] >= arr[idx - 1]) {
                idx++;
                continue;
            }
            comparisons++;
            this.first = idx;
            this.second = idx - 1;
            notifyObservers();
            delay();
            swap(idx, idx - 1, arr);
            swaps++;
            idx--;
            notifyObservers();
            delay();
        }
    }

    private void stooge_sort(int[] arr, int s, int n) {
        if (!keepSorting)
            return;
        if (n - s <= 2) {
            comparisons++;
            access++;
            access++;
            if (arr[s] > arr[n - 1]) {
                this.first = s;
                this.second = n - 1;
                notifyObservers();
                delay();
                swap(s, n - 1, arr);
                swaps++;
                notifyObservers();
                delay();
            }
            return;
        }
        int two_thirds = (int) Math.ceil((2.0 / 3.0) * (n - s));
        space++;
        stooge_sort(arr, s, s + two_thirds);
        stooge_sort(arr, n - two_thirds, n);
        stooge_sort(arr, s, s + two_thirds);
    }

    private void oddeven_sort(int[] arr) {
        int n = arr.length;
        space++;
        boolean flag = true;
        while (flag) {
            flag = false;
            for (int i = 1; i <= n - 2; i = i + 2) {
                if (!keepSorting)
                    return;
                comparisons++;
                access++;
                access++;
                if (arr[i] > arr[i + 1]) {
                    this.first = i;
                    this.second = i + 1;
                    notifyObservers();
                    delay();
                    swap(i, i + 1, arr);
                    flag = true;
                    swaps++;
                    notifyObservers();
                    delay();
                }
            }

            for (int i = 0; i <= n - 2; i = i + 2) {
                if (!keepSorting)
                    return;
                comparisons++;
                access++;
                access++;
                if (arr[i] > arr[i + 1]) {
                    this.first = i;
                    this.second = i + 1;
                    notifyObservers();
                    delay();
                    swap(i, i + 1, arr);
                    flag = true;
                    swaps++;
                    notifyObservers();
                    delay();
                }
            }
        }
    }

    private void intro_sort(int[] arr) {
        int n = arr.length;
        int depth = 2 * (int) Math.floor(Math.log(n));
        choose(arr, 0, n - 1, depth);
    }

    private void choose(int[] arr, int s, int e, int depth) {
        if (e - s + 1 <= 16) {
            insertion_sort(arr, s, e);
            return;
        }
        if (depth <= 0) {
            heap_sort(arr, s, e);
            return;
        }
        int partition = partition(s, e, arr);
        choose(arr, s, partition - 1, depth - 1);
        choose(arr, partition + 1, e, depth - 1);
    }

}

// strand sort (better for lists not arrays)
/*
 * private ArrayList<Integer> merge(ArrayList<Integer> a, ArrayList<Integer> b)
 * {
 * int i = 0, j = 0;
 * while (i < a.size() && j < b.size()) {
 * if (a.get(i) < b.get(j))
 * b.add(j++, a.get(i++));
 * else
 * j++;
 * }
 * while (i < a.size()) {
 * b.add(a.get(i++));
 * }
 * return b;
 * }
 * 
 * private ArrayList<Integer> strand_sort(ArrayList<Integer> input,
 * ArrayList<Integer> output) {
 * if (!input.isEmpty()) {
 * ArrayList<Integer> sublist = new ArrayList<Integer>();
 * sublist.add(input.remove(0));
 * for (int i = 0; i < input.size(); i++)
 * if (input.get(i) > sublist.get(sublist.size() - 1))
 * sublist.add(input.remove(i--));
 * if (output.isEmpty())
 * output.addAll(sublist);
 * else
 * output = merge(sublist, output);
 * strand_sort(input, output);
 * }
 * return output;
 * }
 * 
 */