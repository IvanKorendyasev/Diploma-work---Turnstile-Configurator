package org.yourorghere;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * GLRenderer.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class GLRenderer implements GLEventListener {

    int rot1=0,rot2=0;
    String TurnstileToRead;
    String ReaderToRead;
    String CameraToRead;
    String SupportToRead;
    String ReaderChoice = "Pike-2.stl";
    Boolean WithCamera = false;
    Boolean TopLight = false;
    Boolean BottomLight = false;
    Boolean WithScanner = true;
    Boolean Reread = true; //как старт в бродилке
    double scale=900; //масштаб
    
    //второй сканер
    String Reader2ToRead;
    String Camera2ToRead;
    String Support2ToRead;
    String Reader2Choice = "Pike-2.stl";
    Boolean WithCamera2 = false;
    Boolean TopLight2 = false;
    Boolean BottomLight2 = false;
    Boolean WithScanner2 = true;
    
    //для записи в файл
    ArrayList<Double> TurnstileToWrite = new ArrayList<Double>();
    ArrayList<Double> ReaderToWrite = new ArrayList<Double>();
    ArrayList<Double> CameraToWrite = new ArrayList<Double>();
    ArrayList<Double> TopLightToWrite = new ArrayList<Double>();
    ArrayList<Double> BottomLightToWrite = new ArrayList<Double>();
    ArrayList<Double> FacePannelToWrite = new ArrayList<Double>();
    ArrayList<Double> Reader2ToWrite = new ArrayList<Double>();
    ArrayList<Double> Camera2ToWrite = new ArrayList<Double>();
    ArrayList<Double> TopLight2ToWrite = new ArrayList<Double>();
    ArrayList<Double> BottomLight2ToWrite = new ArrayList<Double>();
    ArrayList<Double> FacePannel2ToWrite = new ArrayList<Double>();
    ArrayList<Double> Support2ToWrite = new ArrayList<Double>();
    ArrayList<Double> SupportToWrite = new ArrayList<Double>();
    ArrayList<Double> ToWrite = new ArrayList<Double>(); //временно записывается сюда
    
    double height=0;
    double minY=10000;
    double L=0;
    double SecondPoint=0;
    double TurnstileHeight;
    double TopLightHeight;
    double BottomLightHeight;
    double CameraHeight;
    double PikeHeight;
    double FacepannelHeight;
    double SupportHeight;
    
    String OutputText = "Начало работы" + "\n";
    
    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        
        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        glu.gluPerspective(60, 1,  0.1, 200);
        glu.gluLookAt(0, 1.5, 1.5, 0, 0, 0, 0, 0, 1);
        
        gl.glRotatef(rot1, 1,0,0);
        gl.glRotatef(rot2, 0,0,1);
        gl.glTranslatef(0,0.25f,0.3f);
        if (Reread)
        {
            
        //обнуляем все списки на экспорт (иначе в файле сетка может дублироваться)    
        TurnstileToWrite.clear();
        ReaderToWrite.clear();
        TopLightToWrite.clear();
        BottomLightToWrite.clear();
        CameraToWrite.clear();
        FacePannelToWrite.clear();
        Reader2ToWrite.clear();
        TopLight2ToWrite.clear();
        BottomLight2ToWrite.clear();
        Camera2ToWrite.clear();
        FacePannel2ToWrite.clear();
        SupportToWrite.clear();
        Support2ToWrite.clear();
        ToWrite.clear();
        
        height=0;
        TurnstileHeight=0;
        TopLightHeight=0;
        BottomLightHeight=0;
        CameraHeight=0;
        PikeHeight=0;
        FacepannelHeight=0;
        SupportHeight=0;
        SecondPoint =0;
        minY=0;
        L=0;
        
        gl.glNewList(127, GL.GL_COMPILE);
        
        Read(TurnstileToRead, gl,0,0,0,false);
        TurnstileToWrite.addAll(ToWrite);
        ToWrite.clear();
        TurnstileHeight = height;
        height = 0;
        SecondPoint = minY+L;
        
        if ("Pike-2.stl".equals(ReaderChoice))
        {
            Read(SupportToRead, gl,0,0,0,false);
            SupportToWrite.addAll(ToWrite);
            ToWrite.clear();
            SupportHeight = height;
            height = 0;

            if (BottomLight)
            {
                Read("3D Models/Считыватели/Детали Pike-2/С нижним светофором.stl", gl,0,0,SupportHeight,false);
                BottomLightToWrite.addAll(ToWrite);
                ToWrite.clear();
                BottomLightHeight = height;
                height = 0;
            }
            else
            {
                Read("3D Models/Считыватели/Детали Pike-2/Без нижнего светофора.stl", gl,0,0,SupportHeight,false);
                BottomLightToWrite.addAll(ToWrite);
                ToWrite.clear();
                BottomLightHeight = height;
                height = 0;
            }
        
        

            Read(ReaderToRead, gl,0,0,SupportHeight+BottomLightHeight,false);
            ReaderToWrite.addAll(ToWrite);
            ToWrite.clear();
            PikeHeight = height;
            height = 0;

            if (WithScanner)
            {
                Read("3D Models/Считыватели/Детали Pike-2/Со сканером.stl", gl,0,0,SupportHeight+BottomLightHeight,false);
                FacePannelToWrite.addAll(ToWrite);
                ToWrite.clear();
                height = 0;
            }
            else
            {
                Read("3D Models/Считыватели/Детали Pike-2/Без сканера.stl", gl,0,0,SupportHeight+BottomLightHeight,false);
                FacePannelToWrite.addAll(ToWrite);
                ToWrite.clear();
                height = 0;
            }

            if (WithCamera)
            {
                Read(CameraToRead, gl,0,0,SupportHeight+BottomLightHeight+PikeHeight,false);
                CameraToWrite.addAll(ToWrite);
                ToWrite.clear();
                CameraHeight = height;
                height = 0;
            }

            if (TopLight)
            {
                if (WithCamera)
                {
                    Read("3D Models/Считыватели/Детали Pike-2/Верхний светофор.stl", gl,0,0,SupportHeight+BottomLightHeight+PikeHeight+CameraHeight,false);
                    TopLightToWrite.addAll(ToWrite);
                    ToWrite.clear();
                    TopLightHeight = height;
                    height = 0;
                }
                else
                {
                    Read("3D Models/Считыватели/Детали Pike-2/Верхний светофор.stl", gl,0,0,SupportHeight+BottomLightHeight+PikeHeight,false);
                    TopLightToWrite.addAll(ToWrite);
                    ToWrite.clear();
                    TopLightHeight = height;
                    height = 0;
                }
            }
        }
        else
        {
            Read(ReaderToRead, gl,0,0,0,false);
            ReaderToWrite.addAll(ToWrite);
            ToWrite.clear();
        }
        
        //Построение второго сканера
        if ("Pike-2.stl".equals(Reader2Choice))
        {
            Read(Support2ToRead, gl,0,-SecondPoint,0,true);
            Support2ToWrite.addAll(ToWrite);
            ToWrite.clear();
            SupportHeight = height;
            height = 0;

            if (BottomLight2)
            {
                Read("3D Models/Считыватели/Детали Pike-2/С нижним светофором.stl", gl,0,-SecondPoint,SupportHeight,true);
                BottomLight2ToWrite.addAll(ToWrite);
                ToWrite.clear();
                BottomLightHeight = height;
                height = 0;
            }
            else
            {
                Read("3D Models/Считыватели/Детали Pike-2/Без нижнего светофора.stl", gl,0,-SecondPoint,SupportHeight,true);
                BottomLight2ToWrite.addAll(ToWrite);
                ToWrite.clear();
                BottomLightHeight = height;
                height = 0;
            }
        
        

            Read(Reader2ToRead, gl,0,-SecondPoint,SupportHeight+BottomLightHeight,true);
            Reader2ToWrite.addAll(ToWrite);
            ToWrite.clear();
            PikeHeight = height;
            height = 0;

            if (WithScanner2)
            {
                Read("3D Models/Считыватели/Детали Pike-2/Со сканером.stl", gl,0,-SecondPoint,SupportHeight+BottomLightHeight,true);
                FacePannel2ToWrite.addAll(ToWrite);
                ToWrite.clear();
                height = 0;
            }
            else
            {
                Read("3D Models/Считыватели/Детали Pike-2/Без сканера.stl", gl,0,-SecondPoint,SupportHeight+BottomLightHeight,true);
                FacePannel2ToWrite.addAll(ToWrite);
                ToWrite.clear();
                height = 0;
            }

            if (WithCamera2)
            {
                Read(Camera2ToRead, gl,0,-SecondPoint,SupportHeight+BottomLightHeight+PikeHeight,true);
                Camera2ToWrite.addAll(ToWrite);
                ToWrite.clear();
                CameraHeight = height;
                height = 0;
            }

            if (TopLight2)
            {
                if (WithCamera2)
                {
                    Read("3D Models/Считыватели/Детали Pike-2/Верхний светофор.stl", gl,0,-SecondPoint,SupportHeight+BottomLightHeight+PikeHeight+CameraHeight,true);
                    TopLight2ToWrite.addAll(ToWrite);
                    ToWrite.clear();
                    TopLightHeight = height;
                    height = 0;
                }
                else
                {
                    Read("3D Models/Считыватели/Детали Pike-2/Верхний светофор.stl", gl,0,-SecondPoint,SupportHeight+BottomLightHeight+PikeHeight,true);
                    TopLight2ToWrite.addAll(ToWrite);
                    ToWrite.clear();
                    TopLightHeight = height;
                    height = 0;
                }
            }
        }
        else
        {
            Read(Reader2ToRead, gl,0,-SecondPoint,0,true);
            Reader2ToWrite.addAll(ToWrite);
            ToWrite.clear();
        }

        Reread = false;
        gl.glEndList();
        }

        gl.glCallList(127);
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
    public void Read(String Name, GL gl, double tx, double ty, double tz, Boolean Rotate)
    { 
        Double R = 1d;
        if(Rotate)
        {
            R=-1d;
        }
        //tx,ty,tz - смещения по осям x,y,z
        File f = new File(Name);
        try {
            String line;
            ArrayList<Double> vs = new ArrayList<Double>();            
            String coord[];
            Scanner sc= new Scanner(f);
            
            //double min=100000;
            
            
            gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
            gl.glColor3f(1,0,0);
            gl.glBegin(GL.GL_TRIANGLES);
            while(sc.hasNext())
            {
                line = sc.nextLine();
                if (line.contains("vertex"))
                {
                    line = line.replace("         ", "");
                    line = line.replace("vertex ", "");
                    coord = line.split(" ");
                    vs.add(Double.parseDouble(coord[0])/scale+tx/scale);
                    vs.add(R*(Double.parseDouble(coord[1])/scale+ty/scale));
                    vs.add(Double.parseDouble(coord[2])/scale+tz/scale);

                            if(height<Double.parseDouble(coord[2]))
                            {
                                height = Double.parseDouble(coord[2]);
                            }
                            if(L<Double.parseDouble(coord[1])) //расчёт расстояния от начала координат до торца
                            {
                                L = Double.parseDouble(coord[1]);
                            }
                            if(minY>Double.parseDouble(coord[1]))
                            {
                                minY = Double.parseDouble(coord[1]);
                            }

                            //height = height-min;

                    gl.glVertex3d(vs.get(vs.size()-3),vs.get(vs.size()-2),vs.get(vs.size()-1));
                    
                    //для записи в файл
                    ToWrite.add(vs.get(vs.size()-3));
                    ToWrite.add(vs.get(vs.size()-2));
                    ToWrite.add(vs.get(vs.size()-1));
                }
            }
            gl.glEnd();
            
            sc.close();
            sc = null;
            sc = new Scanner(f);
            
            gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_LINE);
            gl.glColor3f(1,1,1);
            gl.glLineWidth(1);
            gl.glBegin(GL.GL_TRIANGLES);
            while(sc.hasNext())
            {
                line = sc.nextLine();
                if (line.contains("vertex"))
                {
                    line = line.replace("         ", "");
                    line = line.replace("vertex ", "");
                    coord = line.split(" ");
                    vs.add(Double.parseDouble(coord[0])/scale+tx/scale);
                    vs.add(R*(Double.parseDouble(coord[1])/scale+ty/scale));
                    vs.add(Double.parseDouble(coord[2])/scale+tz/scale);
                    gl.glVertex3d(vs.get(vs.size()-3),vs.get(vs.size()-2),vs.get(vs.size()-1));
                    
                }
            }
            sc.close();
            sc = null;
            gl.glEnd();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GLRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void write()
    {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.stl","*.*");
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filter);
        if ( fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
            try  {
                FileWriter fw = new FileWriter(fc.getSelectedFile()+".stl");
                fw.write("solid " + "\n");
                for (int i = 8; i <= TurnstileToWrite.size()-1; i=i+9) //9 - кол-во координат для трех точек. Счетчик с 8, чтоб не было выхода за пределы массива
                {
                    fw.write("   facet normal 0 0 0" + "\n" + "      outer loop" + "\n");
                    fw.write("         vertex " + TurnstileToWrite.get(i-8) + " " + TurnstileToWrite.get(i-7) + " " + TurnstileToWrite.get(i-6) + "\n");
                    fw.write("         vertex " + TurnstileToWrite.get(i-5) + " " + TurnstileToWrite.get(i-4) + " " + TurnstileToWrite.get(i-3) + "\n");
                    fw.write("         vertex " + TurnstileToWrite.get(i-2) + " " + TurnstileToWrite.get(i-1) + " " + TurnstileToWrite.get(i) + "\n");
                    fw.write("      endloop" + "\n" + "   endfacet" + "\n");
                }
                for (int i = 8; i <= ReaderToWrite.size()-1; i=i+9) 
                {
                    fw.write("   facet normal 0 0 0" + "\n" + "      outer loop" + "\n");
                    fw.write("         vertex " + ReaderToWrite.get(i-8) + " " + ReaderToWrite.get(i-7) + " " + ReaderToWrite.get(i-6) + "\n");
                    fw.write("         vertex " + ReaderToWrite.get(i-5) + " " + ReaderToWrite.get(i-4) + " " + ReaderToWrite.get(i-3) + "\n");
                    fw.write("         vertex " + ReaderToWrite.get(i-2) + " " + ReaderToWrite.get(i-1) + " " + ReaderToWrite.get(i) + "\n");
                    fw.write("      endloop" + "\n" + "   endfacet" + "\n");
                }
                if ("Pike-2.stl".equals(ReaderChoice))
                {
                    for (int i = 8; i <= SupportToWrite.size()-1; i=i+9) 
                    {
                        fw.write("   facet normal 0 0 0" + "\n" + "      outer loop" + "\n");
                        fw.write("         vertex " + SupportToWrite.get(i-8) + " " + SupportToWrite.get(i-7) + " " + SupportToWrite.get(i-6) + "\n");
                        fw.write("         vertex " + SupportToWrite.get(i-5) + " " + SupportToWrite.get(i-4) + " " + SupportToWrite.get(i-3) + "\n");
                        fw.write("         vertex " + SupportToWrite.get(i-2) + " " + SupportToWrite.get(i-1) + " " + SupportToWrite.get(i) + "\n");
                        fw.write("      endloop" + "\n" + "   endfacet" + "\n");
                    }
                    for (int i = 8; i <= BottomLightToWrite.size()-1; i=i+9) 
                    {
                        fw.write("   facet normal 0 0 0" + "\n" + "      outer loop" + "\n");
                        fw.write("         vertex " + BottomLightToWrite.get(i-8) + " " + BottomLightToWrite.get(i-7) + " " + BottomLightToWrite.get(i-6) + "\n");
                        fw.write("         vertex " + BottomLightToWrite.get(i-5) + " " + BottomLightToWrite.get(i-4) + " " + BottomLightToWrite.get(i-3) + "\n");
                        fw.write("         vertex " + BottomLightToWrite.get(i-2) + " " + BottomLightToWrite.get(i-1) + " " + BottomLightToWrite.get(i) + "\n");
                        fw.write("      endloop" + "\n" + "   endfacet" + "\n");
                    }
                    for (int i = 8; i <= FacePannelToWrite.size()-1; i=i+9) 
                    {
                        fw.write("   facet normal 0 0 0" + "\n" + "      outer loop" + "\n");
                        fw.write("         vertex " + FacePannelToWrite.get(i-8) + " " + FacePannelToWrite.get(i-7) + " " + FacePannelToWrite.get(i-6) + "\n");
                        fw.write("         vertex " + FacePannelToWrite.get(i-5) + " " + FacePannelToWrite.get(i-4) + " " + FacePannelToWrite.get(i-3) + "\n");
                        fw.write("         vertex " + FacePannelToWrite.get(i-2) + " " + FacePannelToWrite.get(i-1) + " " + FacePannelToWrite.get(i) + "\n");
                        fw.write("      endloop" + "\n" + "   endfacet" + "\n");
                    }
                    if (TopLight)
                    {
                        for (int i = 8; i <= TopLightToWrite.size()-1; i=i+9) 
                        {
                        fw.write("   facet normal 0 0 0" + "\n" + "      outer loop" + "\n");
                        fw.write("         vertex " + TopLightToWrite.get(i-8) + " " + TopLightToWrite.get(i-7) + " " + TopLightToWrite.get(i-6) + "\n");
                        fw.write("         vertex " + TopLightToWrite.get(i-5) + " " + TopLightToWrite.get(i-4) + " " + TopLightToWrite.get(i-3) + "\n");
                        fw.write("         vertex " + TopLightToWrite.get(i-2) + " " + TopLightToWrite.get(i-1) + " " + TopLightToWrite.get(i) + "\n");
                        fw.write("      endloop" + "\n" + "   endfacet" + "\n");
                        }
                    }
                    if (WithCamera)
                    {
                        for (int i = 8; i <= CameraToWrite.size()-1; i=i+9) 
                        {
                        fw.write("   facet normal 0 0 0" + "\n" + "      outer loop" + "\n");
                        fw.write("         vertex " + CameraToWrite.get(i-8) + " " + CameraToWrite.get(i-7) + " " + CameraToWrite.get(i-6) + "\n");
                        fw.write("         vertex " + CameraToWrite.get(i-5) + " " + CameraToWrite.get(i-4) + " " + CameraToWrite.get(i-3) + "\n");
                        fw.write("         vertex " + CameraToWrite.get(i-2) + " " + CameraToWrite.get(i-1) + " " + CameraToWrite.get(i) + "\n");
                        fw.write("      endloop" + "\n" + "   endfacet" + "\n");
                        }
                    }
                }
                for (int i = 8; i <= Reader2ToWrite.size()-1; i=i+9) 
                {
                    fw.write("   facet normal 0 0 0" + "\n" + "      outer loop" + "\n");
                    fw.write("         vertex " + Reader2ToWrite.get(i-8) + " " + Reader2ToWrite.get(i-7) + " " + Reader2ToWrite.get(i-6) + "\n");
                    fw.write("         vertex " + Reader2ToWrite.get(i-5) + " " + Reader2ToWrite.get(i-4) + " " + Reader2ToWrite.get(i-3) + "\n");
                    fw.write("         vertex " + Reader2ToWrite.get(i-2) + " " + Reader2ToWrite.get(i-1) + " " + Reader2ToWrite.get(i) + "\n");
                    fw.write("      endloop" + "\n" + "   endfacet" + "\n");
                }
                if ("Pike-2.stl".equals(Reader2Choice))
                {
                    for (int i = 8; i <= Support2ToWrite.size()-1; i=i+9) 
                    {
                        fw.write("   facet normal 0 0 0" + "\n" + "      outer loop" + "\n");
                        fw.write("         vertex " + Support2ToWrite.get(i-8) + " " + Support2ToWrite.get(i-7) + " " + Support2ToWrite.get(i-6) + "\n");
                        fw.write("         vertex " + Support2ToWrite.get(i-5) + " " + Support2ToWrite.get(i-4) + " " + Support2ToWrite.get(i-3) + "\n");
                        fw.write("         vertex " + Support2ToWrite.get(i-2) + " " + Support2ToWrite.get(i-1) + " " + Support2ToWrite.get(i) + "\n");
                        fw.write("      endloop" + "\n" + "   endfacet" + "\n");
                    }
                    for (int i = 8; i <= BottomLight2ToWrite.size()-1; i=i+9) 
                    {
                        fw.write("   facet normal 0 0 0" + "\n" + "      outer loop" + "\n");
                        fw.write("         vertex " + BottomLight2ToWrite.get(i-8) + " " + BottomLight2ToWrite.get(i-7) + " " + BottomLight2ToWrite.get(i-6) + "\n");
                        fw.write("         vertex " + BottomLight2ToWrite.get(i-5) + " " + BottomLight2ToWrite.get(i-4) + " " + BottomLight2ToWrite.get(i-3) + "\n");
                        fw.write("         vertex " + BottomLight2ToWrite.get(i-2) + " " + BottomLight2ToWrite.get(i-1) + " " + BottomLight2ToWrite.get(i) + "\n");
                        fw.write("      endloop" + "\n" + "   endfacet" + "\n");
                    }
                    for (int i = 8; i <= FacePannel2ToWrite.size()-1; i=i+9) 
                    {
                        fw.write("   facet normal 0 0 0" + "\n" + "      outer loop" + "\n");
                        fw.write("         vertex " + FacePannel2ToWrite.get(i-8) + " " + FacePannel2ToWrite.get(i-7) + " " + FacePannel2ToWrite.get(i-6) + "\n");
                        fw.write("         vertex " + FacePannel2ToWrite.get(i-5) + " " + FacePannel2ToWrite.get(i-4) + " " + FacePannel2ToWrite.get(i-3) + "\n");
                        fw.write("         vertex " + FacePannel2ToWrite.get(i-2) + " " + FacePannel2ToWrite.get(i-1) + " " + FacePannel2ToWrite.get(i) + "\n");
                        fw.write("      endloop" + "\n" + "   endfacet" + "\n");
                    }
                    if (TopLight2)
                    {
                        for (int i = 8; i <= TopLight2ToWrite.size()-1; i=i+9) 
                        {
                        fw.write("   facet normal 0 0 0" + "\n" + "      outer loop" + "\n");
                        fw.write("         vertex " + TopLight2ToWrite.get(i-8) + " " + TopLight2ToWrite.get(i-7) + " " + TopLight2ToWrite.get(i-6) + "\n");
                        fw.write("         vertex " + TopLight2ToWrite.get(i-5) + " " + TopLight2ToWrite.get(i-4) + " " + TopLight2ToWrite.get(i-3) + "\n");
                        fw.write("         vertex " + TopLight2ToWrite.get(i-2) + " " + TopLight2ToWrite.get(i-1) + " " + TopLight2ToWrite.get(i) + "\n");
                        fw.write("      endloop" + "\n" + "   endfacet" + "\n");
                        }
                    }
                    if (WithCamera2)
                    {
                        for (int i = 8; i <= Camera2ToWrite.size()-1; i=i+9) 
                        {
                        fw.write("   facet normal 0 0 0" + "\n" + "      outer loop" + "\n");
                        fw.write("         vertex " + Camera2ToWrite.get(i-8) + " " + Camera2ToWrite.get(i-7) + " " + Camera2ToWrite.get(i-6) + "\n");
                        fw.write("         vertex " + Camera2ToWrite.get(i-5) + " " + Camera2ToWrite.get(i-4) + " " + Camera2ToWrite.get(i-3) + "\n");
                        fw.write("         vertex " + Camera2ToWrite.get(i-2) + " " + Camera2ToWrite.get(i-1) + " " + Camera2ToWrite.get(i) + "\n");
                        fw.write("      endloop" + "\n" + "   endfacet" + "\n");
                        }
                    }
                }
                fw.write("endsolid");
                fw.flush();
                OutputText = OutputText + "Готово" + "\n";
                } 
            catch (IOException ex) {
                Logger.getLogger(GLRenderer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

