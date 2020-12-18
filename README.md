Bonjour Monsieur,


Pour utiliser la base il suffit de créer juste une base de donnée et 
en lancant le programme toute les tables vont être crée.

create database db_progWebProject;
create user 'admin'@'%' identified by 'adminPassword';
grant all on db_progWebProject.* to 'admin'@'%';

Les informations de la base de données sont dans le fichier application.properties
vous pouvez mettre vos propre information le code au dessus est la pour vous faciliter
la tâche.Si vous mettez vos information n'oubliez pas de changer les informatioins dans 
le fichier application.properties.




