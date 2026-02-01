export interface TeamComposition {
  id?: number;
  name: string;
  description: string;
  blueTop?: number;
  blueJungle?: number;
  blueMid?: number;
  blueAdc?: number;
  blueSupport?: number;
  redTop?: number;
  redJungle?: number;
  redMid?: number;
  redAdc?: number;
  redSupport?: number;
  createdAt?: Date;
}

export interface TeamState {
  blue: { [key: string]: any };
  red: { [key: string]: any };
}
