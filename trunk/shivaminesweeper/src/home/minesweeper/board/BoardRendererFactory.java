package home.minesweeper.board;


/** 
 * Creates one (and only one) instance of the 
 * <code>BoardRenderer</code> class and ensures that
 * clients receive only that instance.
 * TODO must be initialized in a static initialization block.
 */
final public class BoardRendererFactory {

		
			
			
				
				
				private BoardRendererFactory(){
				/*
				 * Does nothing.
				 */ }

				/**
				 * @uml.property  name="INSTANCE" readOnly="true"
				 */
				private static BoardRenderer INSTANCE;

					
						
						
						public static BoardRenderer returnBoardRendererAsSingleton(){
						
												return null;
											 }

}
