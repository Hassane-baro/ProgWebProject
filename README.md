Bonjour Monsieur,


Pour utiliser la base il suffit de cr�er juste une base de donn�e et 
en lancant le programme toute les tables vont �tre cr�e.

create database db_progWebProject;
create user 'admin'@'%' identified by 'adminPassword';
grant all on db_progWebProject.* to 'adminPassword'@'%';

Les informations de la base de donn�es sont dans le fichier application.properties
vous pouvez mettre vos propre information le code au dessus est la pour vous faciliter
la t�che.Si vous mettez vos information n'oubliez pas de changer les informatioins dans 
le fichier application.properties.




