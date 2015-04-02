package com.ollieread.technomagi.client.gui.content;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.specialisation.Specialisation;
import com.ollieread.technomagi.client.gui.component.Component.ComponentAlignment;
import com.ollieread.technomagi.client.gui.component.ComponentRecipe.RecipeType;
import com.ollieread.technomagi.client.gui.content.element.Element;
import com.ollieread.technomagi.client.gui.content.element.ElementImage;
import com.ollieread.technomagi.client.gui.content.element.ElementParagraph;
import com.ollieread.technomagi.client.gui.content.element.ElementRecipe;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.util.PlayerHelper;
import com.ollieread.technomagi.util.ResourceHelper;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;

public class ContentLoader
{

    protected static Map<String, Content> content = new LinkedHashMap<String, Content>();

    public static Content get(String name, String path)
    {
        if (content.containsKey(name)) {
            return content.get(name);
        } else {
            Content loaded = load(path);

            if (loaded != null) {
                content.put(name, loaded);
                return loaded;
            }
        }

        return null;
    }

    public static Content load(String path)
    {
        try {
            InputStream resource = Loader.instance().activeModContainer().getMod().getClass().getResourceAsStream(path);

            if (resource != null) {
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document document = builder.parse(resource);
                Node collection = document.getFirstChild();

                if (collection.getNodeName().equals("content")) {
                    NodeList sections = collection.getChildNodes();
                    Content content = new Content();

                    for (int i = 0; i < sections.getLength(); i++) {
                        Node sectionNode = sections.item(i);

                        if (sectionNode.getNodeName().equals("section")) {
                            String name = sectionNode.getAttributes().getNamedItem("name").getNodeValue();
                            int pageCount = 0;
                            Map<Integer, List<Element>> pageMap = new LinkedHashMap<Integer, List<Element>>();
                            NodeList pages = sectionNode.getChildNodes();
                            String prefix = "section_" + name;

                            for (int x = 0; x < pages.getLength(); x++) {
                                Node page = pages.item(x);

                                if (page.getNodeName().equals("page")) {
                                    pageMap.put(pageCount, getElements(prefix + "_" + x, page));
                                    pageCount++;
                                }
                            }

                            content.addSection(new Section(name, pageMap));
                        }
                    }

                    return content;
                }
            }
        } catch (ParserConfigurationException e) {
            if (Technomagi.debug) {
                e.printStackTrace();
            }
            return null;
        } catch (SAXException e) {
            if (Technomagi.debug) {
                e.printStackTrace();
            }
            return null;
        } catch (IOException e) {
            if (Technomagi.debug) {
                e.printStackTrace();
            }
            return null;
        } catch (IllegalArgumentException e) {
            if (Technomagi.debug) {
                e.printStackTrace();
            }
            return null;
        }

        return null;
    }

    public static List<Element> getElements(String prefix, Node node)
    {
        List<Element> elements = new ArrayList<Element>();
        NodeList children = node.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            Element element = null;

            if (child.getNodeName().equals("paragraph")) {
                element = getParagraph(prefix + "_paragraph_" + i, child);
            } else if (child.getNodeName().equals("image")) {
                element = getImage(prefix + "_image_" + i, child);
            } else if (child.getNodeName().equals("recipe")) {
                element = getRecipe(prefix + "_recipe_" + i, child);
            }

            if (element != null) {
                elements.add(element);
            }
        }

        return elements;
    }

    public static ElementParagraph getParagraph(String name, Node node)
    {
        ElementParagraph element = new ElementParagraph(name, node.getTextContent());
        NamedNodeMap attributes = node.getAttributes();
        int paddingX = 0;
        int paddingY = 0;
        int offsetX = 0;
        int offsetY = 0;

        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            String attributeName = attribute.getNodeName();

            if (attributeName.equals("alignment")) {
                String alignmentText = attribute.getNodeValue();
                ComponentAlignment alignment = null;

                if (alignmentText.equals("right")) {
                    alignment = ComponentAlignment.RIGHT;
                } else if (alignmentText.equals("center")) {
                    alignment = ComponentAlignment.CENTER;
                } else {
                    alignment = ComponentAlignment.LEFT;
                }

                element.setAlignment(alignment);
            } else if (attributeName.equals("paddingX")) {
                paddingX = Integer.valueOf(attribute.getNodeValue());
            } else if (attributeName.equals("paddingY")) {
                paddingY = Integer.valueOf(attribute.getNodeValue());
            } else if (attributeName.equals("offsetX")) {
                offsetX = Integer.valueOf(attribute.getNodeValue());
            } else if (attributeName.equals("offsetY")) {
                offsetY = Integer.valueOf(attribute.getNodeValue());
            } else if (attributeName.equals("multiplayer")) {
                boolean multiplayer = Boolean.valueOf(attribute.getNodeValue());

                if (Config.multiplayerMode != multiplayer) {
                    return null;
                }
            } else if (attributeName.equals("specialisation")) {
                Specialisation specialisation = TechnomagiApi.specialisation().getSpecialisation(attribute.getNodeValue());

                if (specialisation != null && !PlayerHelper.isSpecialisation(Technomagi.proxy.getClientPlayer(), specialisation)) {
                    return null;
                }
            } else if (attributeName.equals("knowledge")) {
                Knowledge knowledge = TechnomagiApi.getKnowledge(attribute.getNodeValue());

                if (knowledge != null && !PlayerHelper.hasKnowledge(Technomagi.proxy.getClientPlayer(), knowledge)) {
                    return null;
                }
            }
        }

        element.setPadding(paddingX, paddingY);
        element.setOffset(offsetX, offsetY);

        return element;
    }

    public static ElementImage getImage(String name, Node node)
    {
        ElementImage element = new ElementImage(name);
        NamedNodeMap attributes = node.getAttributes();
        int paddingX = 0;
        int paddingY = 0;
        int offsetX = 0;
        int offsetY = 0;
        int u = 0;
        int v = 0;

        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            String attributeName = attribute.getNodeName();

            if (attributeName.equals("alignment")) {
                String alignmentText = attribute.getNodeValue();
                ComponentAlignment alignment = null;

                if (alignmentText.equals("right")) {
                    alignment = ComponentAlignment.RIGHT;
                } else if (alignmentText.equals("center")) {
                    alignment = ComponentAlignment.CENTER;
                } else {
                    alignment = ComponentAlignment.LEFT;
                }

                element.setAlignment(alignment);
            } else if (attributeName.equals("paddingX")) {
                paddingX = Integer.valueOf(attribute.getNodeValue());
            } else if (attributeName.equals("paddingY")) {
                paddingY = Integer.valueOf(attribute.getNodeValue());
            } else if (attributeName.equals("offsetX")) {
                offsetX = Integer.valueOf(attribute.getNodeValue());
            } else if (attributeName.equals("offsetY")) {
                offsetY = Integer.valueOf(attribute.getNodeValue());
            } else if (attributeName.equals("u")) {
                u = Integer.valueOf(attribute.getNodeValue());
            } else if (attributeName.equals("v")) {
                v = Integer.valueOf(attribute.getNodeValue());
            } else if (attributeName.equals("resource")) {
                element.setImage(ResourceHelper.texture(attribute.getNodeValue()));
            } else if (attributeName.equals("multiplayer")) {
                boolean multiplayer = Boolean.valueOf(attribute.getNodeValue());

                if (Config.multiplayerMode != multiplayer) {
                    return null;
                }
            } else if (attributeName.equals("specialisation")) {
                Specialisation specialisation = TechnomagiApi.specialisation().getSpecialisation(attribute.getNodeValue());

                if (specialisation != null && !PlayerHelper.isSpecialisation(Technomagi.proxy.getClientPlayer(), specialisation)) {
                    return null;
                }
            } else if (attributeName.equals("knowledge")) {
                Knowledge knowledge = TechnomagiApi.getKnowledge(attribute.getNodeValue());

                if (knowledge != null && !PlayerHelper.hasKnowledge(Technomagi.proxy.getClientPlayer(), knowledge)) {
                    return null;
                }
            }
        }

        element.setPadding(paddingX, paddingY);
        element.setOffset(offsetX, offsetY);
        element.setUV(u, v);

        return element;
    }

    public static ElementRecipe getRecipe(String name, Node node)
    {
        ElementRecipe element = new ElementRecipe(name);
        NamedNodeMap attributes = node.getAttributes();
        int paddingX = 0;
        int paddingY = 0;
        int offsetX = 0;
        int offsetY = 0;
        RecipeType type = RecipeType.VANILLA;
        ItemStack output = null;

        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            String attributeName = attribute.getNodeName();

            if (attributeName.equals("alignment")) {
                String alignmentText = attribute.getNodeValue();
                ComponentAlignment alignment = null;

                if (alignmentText.equals("right")) {
                    alignment = ComponentAlignment.RIGHT;
                } else if (alignmentText.equals("center")) {
                    alignment = ComponentAlignment.CENTER;
                } else {
                    alignment = ComponentAlignment.LEFT;
                }

                element.setAlignment(alignment);
            } else if (attributeName.equals("paddingX")) {
                paddingX = Integer.valueOf(attribute.getNodeValue());
            } else if (attributeName.equals("paddingY")) {
                paddingY = Integer.valueOf(attribute.getNodeValue());
            } else if (attributeName.equals("offsetX")) {
                offsetX = Integer.valueOf(attribute.getNodeValue());
            } else if (attributeName.equals("offsetY")) {
                offsetY = Integer.valueOf(attribute.getNodeValue());
            } else if (attributeName.equals("type")) {
                String typeText = attribute.getNodeValue();

                if (typeText.equals("furnace")) {
                    type = RecipeType.FURNACE;
                }
            } else if (attributeName.equals("output")) {
                String[] outputParts = attribute.getNodeValue().split(":");
                String objectName = "";
                Item item = null;
                int damage = 0;

                if (outputParts.length == 3) {
                    objectName = outputParts[1] + ":" + outputParts[2];
                } else if (outputParts.length == 4) {
                    objectName = outputParts[1] + ":" + outputParts[2];
                    damage = Integer.valueOf(outputParts[3]);
                }

                if (outputParts[0].equals("item")) {
                    item = GameData.getItemRegistry().getObject(objectName);
                } else {
                    Block block = GameData.getBlockRegistry().getObject(objectName);

                    if (block != null) {
                        item = Item.getItemFromBlock(block);
                    }
                }

                if (item != null) {
                    output = new ItemStack(item, 1, damage);
                } else {
                    return null;
                }
            } else if (attributeName.equals("multiplayer")) {
                boolean multiplayer = Boolean.valueOf(attribute.getNodeValue());

                if (Config.multiplayerMode != multiplayer) {
                    return null;
                }
            } else if (attributeName.equals("specialisation")) {
                Specialisation specialisation = TechnomagiApi.specialisation().getSpecialisation(attribute.getNodeValue());

                if (specialisation != null && !PlayerHelper.isSpecialisation(Technomagi.proxy.getClientPlayer(), specialisation)) {
                    return null;
                }
            } else if (attributeName.equals("knowledge")) {
                Knowledge knowledge = TechnomagiApi.getKnowledge(attribute.getNodeValue());

                if (knowledge != null && !PlayerHelper.hasKnowledge(Technomagi.proxy.getClientPlayer(), knowledge)) {
                    return null;
                }
            }
        }

        element.setPadding(paddingX, paddingY);
        element.setOffset(offsetX, offsetY);
        element.setType(type);
        element.setOutput(output);

        return element;
    }

}
