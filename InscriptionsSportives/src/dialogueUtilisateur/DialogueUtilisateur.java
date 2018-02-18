package dialogueUtilisateur;

import commandLineMenus.*;

class DialogueUtilisateur 
{
	public void candidat()
	{
		
	}
	
	public static void main(String[] args)
	{
		Menu inscriptionMenu = new Menu("Inscription sportive");
		Menu Inscription = new Menu("Inscription", "1");
		inscriptionMenu.add(Inscription);
		inscriptionMenu.addQuit("2");
		Menu candidat = new Menu("Inscription d'un candidat");
		Inscription.add(candidat);
		Inscription.add(
				new Option("Creer une competition", "1", new Action()
				{
					public void optionSelected()
					{
						System.out.println("competition");
					}
				}));
		
		Inscription.add(
				new Option("Inscrire un candidat", "2", new Action()
				{
					public void optionSelected()
					{
						candidat.add(
								new Option("Inscrire une equipe", "1", new Action()
								{
									public void optionSelected()
									{
										System.out.println("equipe");
									}
								}));
						
						candidat.add(
								new Option("Inscrire une personne", "2", new Action()
								{
									public void optionSelected()
									{
										System.out.println("personne");
									}
								}));

						candidat.addBack("3");
					}
				}));
		
		Inscription.addBack("3");
		inscriptionMenu.start();
	}
}
