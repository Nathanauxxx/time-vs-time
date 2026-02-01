export interface Champion {
  id: number;
  name: string;
  championClass: string;
  role?: string;
  iconUrl?: string;
  lanes: string;
  physicalDamage: number;
  magicDamage: number;
  tankiness: number;
  crowdControl: number;
}
