import java.util.*;
 
class Node {
   
    public Node(int noeud) {
        this.noeud = noeud;
        linkedListNode = new LinkedList<Node>();
    }
    
    public void ajouterNoeud(Node noeud) {
        linkedListNode.add(noeud);
    }
    
    public void supprimerNoeud(Node noeud) {
        linkedListNode.remove(noeud);
    }
    
    public Node[] getNodesTab() {
        return linkedListNode.toArray(new Node[linkedListNode.size()]);
    }
    
    public int noeud() {
        return noeud;
    }
    
    private final int noeud;
    private LinkedList<Node> linkedListNode;
    
}

class Chemin {
    private LinkedList<Node> linkedNodes;
    
    public Node getNode(int i) {
        return linkedNodes.get(i);
    }
 
    public int getTaille() {
        return linkedNodes.size();
    }
    
    public void ajouterFirst(Node noeud) {
        linkedNodes.addFirst(noeud);
    }
 
    public Chemin() {
        linkedNodes = new LinkedList<Node>();
    }
    
   
    public Chemin (Node noeudDebut, Node noeudFinal, Node[] noeudTab) {
        linkedNodes = new LinkedList<Node>();
        int N = noeudTab.length;
        
        boolean[] explored = new boolean[N];
        for (int i=0; i < N ;i++) 
            explored[i] = false;
        explored[noeudDebut.noeud()] = true;
        
        int[] precedent = new int[N];
        for (int i=0; i < N; i++) 
            precedent[i] = -1;
        
       
        LinkedList<Node> listNoeud = new LinkedList<Node>();
        listNoeud.add(noeudDebut);
        Node noeud = null;
        
        while((listNoeud.size() != 0) && (noeud = listNoeud.remove()) != noeudFinal) {
            for (Node node : noeud.getNodesTab()) {
                if(!explored[node.noeud()]) {
                    explored[node.noeud()] = true;
                    precedent[node.noeud()] = noeud.noeud();
                    listNoeud.add(node);
                }
            }
        }
        
        if (noeud == noeudFinal) {
            while(noeud != noeudDebut) {
                this.ajouterFirst(noeud);
                noeud = noeudTab[precedent[noeud.noeud()]];
            }
            this.ajouterFirst(noeud);
        }
    } 
}
 
 
class Player {
 
    public static void suppimerNoeud(Node n1, Node n2){
        n1.supprimerNoeud(n2);
        n2.supprimerNoeud(n1);
        System.out.println(n1.noeud() + " " + n2.noeud());
    }
 
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        // Scanning number of nodes, links, and exits
        int N = in.nextInt();
        int L = in.nextInt();
        int E = in.nextInt();
        // Initializing an array of non-linked nodes
        Node[] nodes = new Node[N];
        for (int i = 0 ; i < N ; i++) 
            nodes[i] = new Node(i);
        // Linking nodes
        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt();
            int N2 = in.nextInt();
            nodes[N1].ajouterNoeud(nodes[N2]);
            nodes[N2].ajouterNoeud(nodes[N1]);
        }
        // Initializing an array of exit nodes
        Node[] exits = new Node[E];
        for (int i = 0; i < E; i++)
            exits[i] = nodes[in.nextInt()];
 
        while (true) {
            // Skynet agent position
            int SI = in.nextInt(); 
            int tailleCheminMin = Integer.MAX_VALUE;
            Chemin[] courtChemin = new Chemin[E];
            
            Chemin cheminOut = null;
 
            for (int i = 0 ; i < E ; i++) {
                courtChemin[i] = new Chemin(nodes[SI], exits[i], nodes);
                int taille = courtChemin[i].getTaille();
                if ((taille != 0) && (taille < tailleCheminMin)) {
                    tailleCheminMin = taille;
                    cheminOut = courtChemin[i];
                }
            }
            
            suppimerNoeud(cheminOut.getNode(0), cheminOut.getNode(1)); 
        }
    }
}
  
