/*
  Author : Sabarinath N P ( CS10B020 )
  File : MacroJava to MiniJava parser
  Data : 31 Aug 2012
*/

%{
#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#define MAX_SIZE 1048576	// 2^20
#define MACRO_SIZE 102400	// 100 * 2^10
extern FILE* yyin;

extern int yyparse();

typedef struct macro_ macro;

struct macro_{
  char* name;
  int no_arg;
  char* out;
  struct macro_* next;
};

typedef struct Llist_ Llist;

struct Llist_{
  macro* head;
};

Llist* linkedlist = NULL;

void Initialize(){
  if( linkedlist == NULL ){ 
    linkedlist = (Llist*)malloc(sizeof(Llist));
    linkedlist->head = NULL; 
  }
}

void Destructor(){
  macro* temp1, *temp;
  if( linkedlist != NULL ){ 
    for( temp = linkedlist->head; temp != NULL; ){
      temp1 = temp->next;
      free(temp);
      temp = temp1; 
    }
    free( linkedlist );
  }
}

char* EmptyString(){
  char* to_return = malloc(2);
  sprintf(to_return,"");
  return to_return;
}

char* ModifyOutput( char* arg, int size, char* exp ){
  char* outstr = malloc( MACRO_SIZE );
  char tokens[size][MACRO_SIZE];
  int pos[size+1];
  int i, count, j, k, len;
  pos[0] = 0;
  pos[size] = strlen(arg)+1;
  for( i = 1, count = 1; i < strlen(arg); ++i)
    if( arg[i] == ',' )
      pos[count++] = i+1;
  for( i = 0; i < size; ++i ){
    for( j = 0; j < pos[i+1]-pos[i]-1; ++j)
      tokens[i][j] = arg[ pos[i] + j];
    tokens[i][j] = '\0';
  }
  for( i = 0, count = 0; i < strlen(exp); ){
    for( j = 0; j < size; ++j)
      if( exp[i] == tokens[j][0] )
	break;
    if( j != size ){
      len = strlen(tokens[j]);
      for( k = 1; i + k < strlen(exp) && k < len; ++k ){
	if( exp[i+k] != tokens[j][k] )
	  break;
      }
      // Check whether the token is present
      if( k != len || ( exp[i+k] > 47 && exp[i+k] < 58 ) || ( exp[i+k] > 64 && exp[i+k] < 91 ) || (exp[i+k] > 96 && exp[i+k] < 123) || exp[i+k] == '_' )
	outstr[count++] = exp[i++];
      else{
	char* to_print = malloc( MACRO_SIZE );
	sprintf(to_print,"#^%d", j);
	strncpy( outstr + count, to_print, strlen(to_print) );
	i += len;
	count += strlen(to_print);
	free(to_print);
      }
    }
    else
      outstr[count++] = exp[i++];
  }
  outstr[count] = '\0';
  return outstr;
}

int NumExpression(char* arr){
  int i, count;
  i = count = 0;
  while( i < strlen(arr) )
    if(arr[i++] == ',' )
      ++count;
  return arr[0] != '\0' ? count+1 : count;
}

void MacroStore(char* name, char* arg, char* exp){
  macro* temp = (macro*)malloc( sizeof(macro) );
  temp->name = malloc( MACRO_SIZE );
  temp->out = malloc( MACRO_SIZE );
  sprintf(temp->name,"%s", name);
  temp->no_arg = NumExpression(arg);
  sprintf(temp->out,"%s", temp->no_arg == 0 ? exp : ModifyOutput( arg, temp->no_arg, exp ) );
  temp->next = linkedlist->head;
  linkedlist->head = temp;
}

char* ModifyExpression( char* exp, macro* temp ){
  int i, count, size, j, len;
  size = temp->no_arg;
  char tokens[size][MACRO_SIZE];
  char* outstr = malloc( MACRO_SIZE );
  char* t;
  int pos[size+1];
  pos[0] = 0;
  pos[size] = strlen(exp)+1;
  for( i = 1, count = 1; i < strlen(exp); ++i)
    if( exp[i] == ',' )
      pos[count++] = i+1;
  for( i = 0; i < size; ++i ){
    for( j = 0; j < pos[i+1]-pos[i]-1; ++j)
      tokens[i][j] = exp[ pos[i] + j];
    tokens[i][j] = '\0';
  }
  t = temp->out;
  len = strlen(temp->out);
  for( i = 0, count = 0; i < len; ){
    if( i < len-2 && t[i] == '#' && t[i+1] == '^' && t[i+2] > 47 && t[i+2] < 58){
      j = 2;
      while( i+j < len && t[i+j] < 58 && t[i+j] > 47 )
	j++;
      j -= 2;
      char num[10];
      strncpy( num, t+(i+2), j+1 );
      strncpy( outstr + count, tokens[ atoi(num) ], strlen( tokens[atoi(num)]) );
      count += strlen( tokens[ atoi(num) ] );
      i += 2 + j;
    }
    else
      outstr[count++] = t[i++];
  }
  outstr[count] = '\0';
  return outstr;
}


%}

%union{
	int ival; // integers
	char *bval;// true and false
	char  *kw; // such as class, int, boolean etc
	char *op; // such as +, -, * etc
	char *id; // identifiers
	char *sval; // for Strings
}

%token <ival> INTVAL
%token <bval> BOOLVAL
%token <kw> KEYWORD
%token <op> OPERATOR
%token <id> IDENTIFIER
%token <sval> STRINGVAL
%token THIS
%token NEW
%token RETURN
%token CLASS
%token DEFINE
%token PUBLIC
%token STATIC
%token VOID
%token INT
%token BOOLEAN
%token IF
%token ELSE
%token WHILE
%token EXTENDS
%token STRING
%token PRINTSTMT
%token LENGTH
%token MAIN
%glr-parser
%type <sval> Goal MacroDefinitionTemp TypeDeclarationTemp MainClass TypeDeclaration TempType MethodDeclarationTemp MethodDeclaration Argument ArugmentSecondTemp StatementTemp Statement Type Expression ExpressionParam PrimaryExpression MacroDefinition MacroDefExpression MacroDefStatement IDENTIFIERParam

%%

  Goal:
    MacroDefinitionTemp MainClass TypeDeclarationTemp { Destructor(); printf("%s\n%s", $2, $3 ); free($2); free($3);}
  ;
  
  MacroDefinitionTemp:
    MacroDefinitionTemp MacroDefinition	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return, "%s\n%s", $1, $2 ); free($1); free($2);	$$ = to_return; }
  |	{ $$ = EmptyString(); }
  ;
  
  TypeDeclarationTemp:
    TypeDeclarationTemp TypeDeclaration { char* to_return = malloc( MAX_SIZE ); sprintf(to_return, "%s\n%s", $1, $2 ); free($1); free($2); $$ = to_return; }
  |	{ $$ = EmptyString(); }
  ;
  
  MainClass:
    CLASS IDENTIFIER '{' PUBLIC STATIC VOID MAIN '(' STRING '[' ']' IDENTIFIER ')'  Statement '}' { char* to_return = malloc( MAX_SIZE ); sprintf(to_return, "class %s {\n\tpublic static void main (String[] %s) %s \n}", $2, $12, $14); free($12); $$ = to_return; }
  ;
  
  TypeDeclaration:
    CLASS IDENTIFIER '{' TempType MethodDeclarationTemp '}'	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"class %s {\t%s\t%s\n}", $2, $4, $5 ); free($4); free($5); $$ = to_return; }
  | CLASS IDENTIFIER EXTENDS IDENTIFIER '{' TempType MethodDeclarationTemp '}'	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"class %s extends %s {\t%s\n\t%s\n}", $2, $4, $6, $7 ); free($6); free($7); $$ = to_return; }
  ;
  
  TempType:
    TempType Type IDENTIFIER ';'	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"\t%s\n\t%s %s;", $1, $2, $3); free($1); free($2); $$ = to_return; }
  |	{ $$ = EmptyString(); }
  ;
  
  MethodDeclarationTemp:
    MethodDeclarationTemp MethodDeclaration	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"\t%s\n\t%s", $1, $2); free($1); free($2); $$ = to_return; }
  |	{ $$ = EmptyString(); }
  ;
  
  MethodDeclaration:
    PUBLIC Type IDENTIFIER '(' Argument ')' '{'  ArugmentSecondTemp StatementTemp RETURN Expression ';' '}'	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return, "public %s %s ( %s ) {\n\t%s\t%s\n\treturn %s;\n}", $2, $3, $5, $8, $9, $11); free($2); free($5); free($8); free($9); free($11); $$ = to_return; }
  ;
  
  Argument:
    Argument ',' Type IDENTIFIER	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s , %s %s", $1, $3, $4); free($1); free($3); $$ = to_return; }
  | Type IDENTIFIER	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s %s", $1, $2); free($1); $$ = to_return; }
  |	{ $$ = EmptyString(); }
  ;
  
  ArugmentSecondTemp:
    ArugmentSecondTemp Type IDENTIFIER ';'	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"\t%s\n\t%s %s ;", $1, $2, $3); free($1); free($2); $$ = to_return; }
  |	{ $$ = EmptyString(); }
  ;
  
  StatementTemp:
    StatementTemp Statement	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"\t%s\t\n%s", $1, $2); free($1); free($2); $$ = to_return; }
  |	{ $$ = EmptyString(); }
  ;
  
  Type:
    BOOLEAN	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"boolean"); $$ = to_return; }
  | INT		{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"int"); $$ = to_return; }
  | IDENTIFIER	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s", $1 ); $$ = to_return; }
  | Type '[' ']'{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s[]", $1); free($1); $$ = to_return; }
  ;

  Statement:
    '{' StatementTemp '}'	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"\t{\t%s\n\t}", $2); free($2); $$ = to_return; }
  | PRINTSTMT '(' Expression ')' ';'	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"\tSystem.out.println(%s);", $3); free($3); $$ = to_return; }
  | IDENTIFIER '=' Expression ';'	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"\t%s = %s;", $1, $3); free($3); $$ = to_return; }
  | IDENTIFIER '[' Expression ']' '=' Expression ';'	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s[ %s ] = %s;", $1, $3, $6); free($3); free($6); $$ = to_return; }
  | IF '(' Expression ')' Statement	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"\tif( %s ) %s", $3, $5); free($3); free($5); $$ = to_return; }
  | IF '(' Expression ')' Statement ELSE Statement	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"\tif( %s )%s\n\telse%s", $3, $5, $7); free($3); free($5); free($7); $$ = to_return; }
  | WHILE '(' Expression ')' Statement	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"\twhile( %s )\t%s", $3, $5); free($3); free($5); $$ = to_return; }
  | IDENTIFIER '(' ExpressionParam ')' ';'	{ char* to_return = malloc( MAX_SIZE ); macro* temp = linkedlist->head; while( temp != NULL ){ if( !strcmp(temp->name, $1 ) && temp->no_arg == NumExpression($3) ){ sprintf(to_return,"\t%s", ModifyExpression($3, temp) ); break; } temp = temp->next; } free($3); $$ = to_return; }
  ;

  Expression:
    PrimaryExpression '<' PrimaryExpression	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s < %s", $1, $3); free($1); free($3); $$ = to_return; }
  | PrimaryExpression '&' PrimaryExpression	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s & %s", $1, $3); free($1); free($3); $$ = to_return; }
  | PrimaryExpression '&' '!' Expression	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s & ! %s", $1, $4); free($1); free($4); $$ = to_return; }
  | '!' Expression '&' '!' Expression		{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"! %s & ! %s", $2, $5); free($2); free($5); $$ = to_return; }
  | '!' Expression '&' PrimaryExpression	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"! %s & %s", $2, $4); free($2); free($4); $$ = to_return; }
  | PrimaryExpression '+' PrimaryExpression	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s + %s", $1, $3); free($1); free($3); $$ = to_return; }
  | PrimaryExpression '-' PrimaryExpression	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s - %s", $1, $3); free($1); free($3); $$ = to_return; }
  | PrimaryExpression '*' PrimaryExpression	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s * %s", $1, $3); free($1); free($3); $$ = to_return; }
  | PrimaryExpression '/' PrimaryExpression	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s / %s", $1, $3); free($1); free($3); $$ = to_return; }
  | PrimaryExpression '[' PrimaryExpression ']'	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"\t%s [ %s ]", $1, $3); free($1); free($3); $$ = to_return; }
  | PrimaryExpression '.' LENGTH		{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"\t%s.length", $1); free($1); $$ = to_return; }
  | PrimaryExpression '.' IDENTIFIER '(' ExpressionParam ')'	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"\t%s.%s( %s )", $1, $3, $5); free($1); free($3); free($5); $$ = to_return; }
  | IDENTIFIER '(' ExpressionParam ')'		{ char* to_return = malloc( MAX_SIZE ); macro* temp = linkedlist->head; while( temp != NULL ){ if( !strcmp(temp->name, $1 ) && temp->no_arg == NumExpression($3) ){ sprintf(to_return,"%s", ModifyExpression($3, temp) ); break; } temp = temp->next; } free($3); $$ = to_return; }
  | PrimaryExpression	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s", $1); free($1); $$ = to_return; }
  | '!' Expression	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"! %s", $2); free($2); $$ =to_return; }
  ;
  
  ExpressionParam:
    ExpressionParam ',' Expression	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s,%s", $1, $3); free($1); free($3); $$ = to_return; }
  | Expression	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s", $1); free($1); $$ = to_return; }
  |	{ $$ = EmptyString(); }
  ;
  
  PrimaryExpression:
    INTVAL	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%d", $1); $$ = to_return; }
  | BOOLVAL	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s", $1); $$ = to_return; }
  | IDENTIFIER	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s", $1); $$ = to_return; }
  | THIS	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"this"); $$ = to_return; }
  | NEW Type '[' Expression ']'	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"new %s[ %s ]", $2, $4); free($2); free($4); $$ = to_return; }
  | NEW IDENTIFIER '(' ')'	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"new %s()", $2); $$ = to_return; }
  | '(' Expression ')'	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"( %s )", $2); free($2); $$ = to_return; }
  ; 
  
  MacroDefinition:
    MacroDefStatement	{ $$ = EmptyString(); }
  | MacroDefExpression	{ $$ = EmptyString(); }
  ;
  
  MacroDefStatement:
    DEFINE IDENTIFIER '(' IDENTIFIERParam ')' '{' StatementTemp '}' { Initialize(); MacroStore($2, $4, $7); free($4); free($7); }
  ;
  
  MacroDefExpression:
    DEFINE IDENTIFIER '(' IDENTIFIERParam ')' '(' Expression ')' { Initialize(); MacroStore($2, $4, $7); free($4); free($7); }
  ;
  
  IDENTIFIERParam:
    IDENTIFIERParam ',' IDENTIFIER	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s,%s", $1, $3); free($1); $$ = to_return; }
  | IDENTIFIER	{ char* to_return = malloc( MAX_SIZE ); sprintf(to_return,"%s", $1); $$ = to_return; }
  |	{ $$ = EmptyString(); }
  ;

%%
main(){
	// parse through the input until there is no more.
	do {
		yyparse();
	} while (!feof(yyin));
}

void yyerror(const char *s){
	printf ("Parse error: %s\n" , s)	;
}