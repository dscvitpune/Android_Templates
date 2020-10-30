#include <bits/stdc++.h>

using namespace std;

string encrypt(string words) {
    string str;
    for(auto i: words){
        if(i != ' '){
            str+=i;
        }
    }    
    int length = str.length();
    int lower =  floor(sqrt(length));
    int upper =  ceil(sqrt(length));

    if((lower*upper) <length){
        lower++;
    }

    vector<vector<char>> result(lower,vector<char>(upper,0));
    int k=0;
    for(int i=0;i<lower;i++){
        for(int j=0;j<upper;j++){
            if(k<length){
                result[i][j]=str[k];
                k++;    
            }
        }
    }
    

    string message="";
    for(int i=0;i<upper;i++){
        for(int j=0;j<lower;j++){
            if(result[j][i]!=0){
            message.push_back(result[j][i]);
            }
        }
        message.push_back(' ');
    }
    message.pop_back();
    return message;
}


int main() {
    string words;
    getline(cin, words);

    string result = encrypt(words);

    // Do not remove below line
    cout << result << "\n";
    // Do not print anything after this line

    return 0;
}