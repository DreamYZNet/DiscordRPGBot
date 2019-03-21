package com.yamizee.discordbots.game;

/**
 * Created by YamiZee on 7/31/2016.
 */
public class Item {
    private String id;
    private String name;
    private String descriptor;
    private String description;
    private int weight; //50kg = very heavy (120lb is about 54kg)
    private int space; //50 = whole backbag
    private int value; //store prize

    public String getId()                               { return id;            }
    public String getName()                             { return name;          }
    public String getDescriptor()                       { return descriptor;    }
    public String getDescription()                      { return description;   }
    public int getWeight()                              { return weight;        }
    public int getSpace()                               { return space;         }
    public int getValue()                               { return value;         }

    public void setId(String id)                        { this.id = id;                     }
    public void setName(String name)                    { this.name = name;                 }
    public void setDescriptor(String descriptor)        { this.descriptor = descriptor;     }
    public void setDescription(String description)      { this.description = description;   }
    public void setWeight(int weight)                   { this.weight = weight;             }
    public void setSpace(int space)                     { this.space = space;               }
    public void setValue(int value)                     { this.value = value;               }
}
