package model.geometry;

public class Face
{
    public int vertIndex[];
    public int coordIndex[];
    public int normalIndex[];

    public Face(int numVertices) {
        vertIndex = new int[numVertices];
        coordIndex = new int[numVertices];
        normalIndex = new int[numVertices];
    }
    
    /** MaterialID is associated with a Face - per 3DS specification */
    public int materialID = 0;     
}