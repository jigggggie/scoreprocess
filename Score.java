import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Student {
	String name;
	int id;
	int kor;
	int eng;
	int math;
	char grade;

	Student(String name, int id, int kor, int eng, int math) {
		this.name = name;
		this.id = id;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		this.grade = getGrade(this.kor, this.eng, this.math);
	}

	Student(String name, int id, char grade) {
		this.name = name;
		this.id = id;
		this.grade = grade;
		setGrade(this.grade);
	}

	public char getGrade(int kor, int eng, int math) {
		int sum = kor + eng + math;
		int avg = sum / 3;
		switch (avg / 10) {
		case 10:
		case 9:
			return 'A';
		case 8:
			return 'B';
		case 7:
			return 'C';
		case 6:
			return 'D';
		default:
			return 'F';
		}
	}

	public void setGrade(char grade) {
		if (grade == 'A') {
			this.kor = 90;
			this.eng = 90;
			this.math = 90;
		} else if (grade == 'B') {
			this.kor = 80;
			this.eng = 80;
			this.math = 80;
		} else if (grade == 'C') {
			this.kor = 70;
			this.eng = 70;
			this.math = 70;
		} else if (grade == 'D') {
			this.kor = 60;
			this.eng = 60;
			this.math = 60;
		} else {
			this.kor = 50;
			this.eng = 50;
			this.math = 50;
		}
	}

	public void printStudent() {
		System.out.println(this.id + " " + this.name + "    " + this.grade);
	}
}

class StudentComparator implements Comparator<Student> {

	@Override
	public int compare(Student arg0, Student arg1) {
		if (arg0.id < arg1.id)
			return -1;
		else if (arg0.id == arg1.id)
			return 0;
		else
			return 1;
	}

}

public class Score {
	public static Student addStudent() {
		Scanner s = new Scanner(System.in);
		System.out.print("이름 : ");
		String name = s.nextLine();
		System.out.print("학번 : ");
		int id = s.nextInt();
		s.nextLine();
		System.out.print("국어성적 : ");
		int kor = s.nextInt();
		s.nextLine();
		System.out.print("영어성적 : ");
		int eng = s.nextInt();
		s.nextLine();
		System.out.print("수학성적 : ");
		int math = s.nextInt();
		s.nextLine();

		return new Student(name, id, kor, eng, math);
	}

	public static void printlist(Vector<Student> list) {
		for (int i = 0; i < list.size(); i++) {
			Student t = list.get(i);
			t.printStudent();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("성적처리 프로그램 ");
		Vector<Student> list = new Vector<Student>();
		File f = new File("score.txt");
		Scanner fs;
		try {
			fs = new Scanner(f);
			while(fs.hasNext()) {
				int fid = Integer.parseInt(fs.next());
				String fname = fs.next();
				char fgrade = fs.next().charAt(0);
				Student t = new Student(fname, fid, fgrade);
				list.add(t);
				Collections.sort(list, new StudentComparator());
			}
		} catch (FileNotFoundException e1) {
			
		}
		
		while (true) {
			System.out.println("1.성적입력  2.전체조회  3.종료 ");
			System.out.print(">>>");
			int sel = s.nextInt();
			switch (sel) {
			case 1:
				list.add(addStudent());
				Collections.sort(list, new StudentComparator());
				break;
			case 2:
				System.out.println("학번              이름       학점");
				System.out.println("==================");
				printlist(list);
				break;
			case 3:
				break;
			}
			if (sel == 3)
				break;
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter("score.txt");
			for (int i = 0; i < list.size(); i++) {
				writer.write(list.get(i).id + " ");
				writer.write(list.get(i).name + " ");
				writer.write(list.get(i).grade + "\r\n");
			}
		} catch (IOException ioe) {
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
			}
		}
	}

}
