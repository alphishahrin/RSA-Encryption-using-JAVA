package rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;



public class Rsa {

    
    public static BigInteger Generate_Random_Prime(int bit){
        BigInteger a = new BigInteger(bit, new Random());
        if(a.mod(BigInteger.valueOf(2)).equals(BigInteger.valueOf(0)))
            a = a.add(BigInteger.valueOf(1));
        while(true)
        {
            if(a.isProbablePrime(1)) // it uses Miller-Rabin primality testing and for numbers greater than 100 bits, Lucas-Lehmer primality testing.
            {            
                break;
            }
            a = a.add(BigInteger.valueOf(2));
        }
        return a;
    }
    static int N  = 100000;
    public static ArrayList<Integer> Seive(){
        ArrayList<Integer> v  =new ArrayList <Integer> ();
        int arr[]  = new int[N+9];
        Arrays.fill(arr, 0);
        arr[1]  = 1;
        for(int i=4;i<=N;i+=2)
            arr[i]  = 1;
        for(int i=3;i*i<=N;i++){
            if(arr[i]==0){
                for(int j = i*i;j<=N;j+= 2*i)
                    arr[j]  =1;
            }
        }
        v.add(2);
        for(int i=3;i<=N;i+=2){
            if(arr[i]==0)
                v.add(i);
        }
        return v;  
    }
    
    public static String ConvertToNumerical(String str){
        String ans = "";
        for(int i=0;i<str.length();i++){
            int x  = str.charAt(i);
            x+= 100;
            ans+= Integer.valueOf(x);     
        }
        return ans;   
    }
    
    public static String ConvertToOrginal(BigInteger temp){
        String p = temp.toString();
        String ans = "",cur;
        for(int i=0;i<p.length();i+=3){
            cur ="";
            cur+= p.charAt(i);
            cur+= p.charAt(i+1);
            cur+= p.charAt(i+2);
            int nmb  = Integer.valueOf(cur);
            ans+= (char)(nmb-100);
        }
        return ans;
    }
    public static void main(String[] args) {
        
       
        System.out.println("Enter number of bits for First Prime Number: ");
        Scanner sc  =new Scanner(System.in);
        int x=  sc.nextInt();
        BigInteger a  = Generate_Random_Prime(x);
        System.out.println("Enter number of bits for Second Prime Number: "); 
        x=  sc.nextInt();
        BigInteger b  = Generate_Random_Prime(x);
        System.out.println("First Prime = "+a);
        System.out.println("Second Prime = "+b);
        
        BigInteger n  = a.multiply(b);
        System.out.println("n = "+n);
        BigInteger temp  = a.subtract(BigInteger.valueOf(1));
        BigInteger temp2  = b.subtract(BigInteger.valueOf(1));
        BigInteger phiN = temp.multiply(temp2);
        System.out.println("Phi(n) = "+phiN);
        ArrayList<Integer>  candidate_e  =Seive();
        
        
        int e = 0;
        for(int i=0;i<candidate_e.size();i++){
            
            if(phiN.mod(BigInteger.valueOf(candidate_e.get(i))).equals(BigInteger.valueOf(0))==false){
                e  =candidate_e.get(i);
                break;
            }
        }
        
        BigInteger E = BigInteger.valueOf(e);
        
        BigInteger D = E.modInverse(phiN);
        
        
        System.out.println("E = "+E);
        System.out.println("D = "+D);
        
        System.out.print("Enter a text: ");
        sc.nextLine();
        String str  =sc.nextLine();
        
        
        BigInteger P = new BigInteger(ConvertToNumerical(str));
        
        System.out.println("Convert to numerical  = "+P);
        
        BigInteger C  = P.modPow(E, n);
        System.out.println("C = "+C);
        
        
        
        P = C.modPow(D, n);
        
        System.out.println("Get back the value  = "+P);
        System.out.println("Orginal Text = "+ConvertToOrginal(P));
        
        
    }
    
}