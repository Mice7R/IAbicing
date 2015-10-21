package IA.util;

public class Quicksort {

	public static void sort(Integer[] list, Integer[] ids, int l, int r)
	{
		if (l < r)
		{
			int p = partition(list, ids, l, r);
			sort(list, ids, l, p - 1);
			sort(list, ids, p + 1, r);
		}
	}

	private static int partition(Integer[] list, Integer[] ids, int l, int r)
	{
		Integer pivot = median(list[l], list[(l + r) / 2], list[r - 1]);
		int i = l;
		for (int j = l; j < r - 1; ++j)
		{
			if (list[j] > pivot)
			{
				swap(list, i, j);
				swap(ids, i++, j);
			}
		}
		swap(list, i, r - 1);
		swap(ids, i, r - 1);
		return i;
	}

	private static void swap(Integer[] sw, int a, int b)
	{
		Integer temp = sw[a];
		sw[a] = sw[b];
		sw[b] = temp;
	}

	private static Integer median(Integer a, Integer b, Integer c)
	{
		if (a > b)
		{
			if (a > c)
			{
				return c;
			} else
			{
				return a;
			}
		} else
		{
			if (b > c)
			{
				return c;
			} else
			{
				return b;
			}
		}
	}

}
